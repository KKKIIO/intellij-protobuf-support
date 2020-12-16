/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kkkiio.intellij.protobuf.golang

import com.goide.GoLanguage
import com.goide.GoTypes
import com.goide.psi.*
import com.google.common.base.CaseFormat
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.*
import com.intellij.util.containers.toArray
import com.jetbrains.rd.util.putUnique
import idea.plugin.protoeditor.gencode.ProtoFromSourceComments
import idea.plugin.protoeditor.lang.psi.*
import java.util.*

@ExperimentalStdlibApi
private val log: Logger = Logger.getInstance(PbGolangGotoDeclarationHandler::class.java)

/** Handles goto declaration from golang generated code to .proto files.  */
@ExperimentalStdlibApi
class PbGolangGotoDeclarationHandler : GotoDeclarationHandler {
    override fun getActionText(dataContext: DataContext): String? {
        return null
    }

    override fun getGotoDeclarationTargets(sourceElement: PsiElement?,
                                           offset: Int,
                                           editor: Editor): Array<PsiElement?>? {
        return (sourceElement?.takeIf {
            it.language.`is`(GoLanguage.INSTANCE)
        } as? LeafPsiElement)?.takeIf {
            it.elementType == GoTypes.IDENTIFIER
        }?.parentOfType<GoReferenceExpressionBase>()?.reference?.resolve()
                ?.let { convertToProtoSymbols(it).takeIf { it.isNotEmpty() } }
                ?.toArray(Array<PsiElement?>(0) { null })
    }

    private fun getGoToPbSymbolMap(pbFile: PbFile) = CachedValuesManager.getCachedValue(pbFile) {
        val map = computeLocalSymbolMap(pbFile)
        log.debug { "computeLocalQualifiedSymbolMap pbFile=$pbFile, result=$map" }
        CachedValueProvider.Result.create(map, PsiModificationTracker.MODIFICATION_COUNT)
    }

    private fun computeLocalSymbolMap(pbFile: PbFile): Map<QualifiedName, PbSymbol> = buildMap {
        fun addEnum(enum: PbEnumDefinition, messageGoName: String?) {
            val enumQualifiedName = enum.qualifiedName ?: return
            val enumGoName = PbGoGenSpec.newGoIdent(enumQualifiedName, pbFile.packageQualifiedName)
            putUnique(QualifiedName.fromComponents(enumGoName), enum)
            enum.enumValues.forEach { symbol ->
                val symbolName = symbol.name ?: return@forEach
                putUnique(QualifiedName.fromComponents(PbGoGenSpec.enumValueName(messageGoName, enumGoName, symbolName)), symbol)
            }
        }

        fun addMsg(msg: PbMessageDefinition, msgName: String) {
            val msgGoName = PbGoGenSpec.goCamelCase(msgName)
            putUnique(QualifiedName.fromComponents(msgGoName), msg)
            val messageBody = msg.body ?: return
            messageBody.simpleFieldList.forEach { pbSimpleField ->
                pbSimpleField.name?.let { symbolName ->
                    putUnique(QualifiedName.fromComponents(msgGoName, PbGoGenSpec.goCamelCase(symbolName)), pbSimpleField)
                }
            }

            messageBody.enumDefinitionList.forEach { pbEnumDefinition ->
                addEnum(pbEnumDefinition, msgGoName)
            }
            // todo: handle more case
        }


        pbFile.symbols.forEach { symbol ->
            val symbolName = symbol.name ?: return@forEach
            when (symbol) {
                is PbMessageDefinition -> addMsg(symbol, symbolName)
                is PbEnumDefinition -> addEnum(symbol, null)
                // todo: handle more case
            }
        }
    }

    /** Reference: https://developers.google.com/protocol-buffers/docs/reference/go-generated  */
    private fun convertToProtoSymbols(element: PsiElement): Collection<PbSymbol?> {
        log.debug("convertToProtoSymbols start, element={javaClass=${element.javaClass}, containingFile=${element.containingFile}}")
        val pbFile = (element.containingFile as? GoFile)?.takeIf {
            it.virtualFile?.name?.endsWith(".pb.go") ?: false
        }?.let {
            ProtoFromSourceComments.findProtoOfGeneratedCode("//", it)
        } ?: return listOf()
        val convertedName = when (element) {
            is GoTypeSpec -> {
                convertTypeSpec(element)
            }
            is GoMethodDeclaration -> {
                convertToProtoFieldOrMethodName(element)
            }
            is GoMethodSpec -> {
                convertToProtoServiceMethodName(element)
            }
            is GoFieldDefinition -> {
                convertToProtoFieldName(element)
            }
            is GoConstDefinition -> {
                convertToProtoEnumValueName(element)
            }
            is GoFunctionDeclaration -> {
                convertToProtoServiceName(element)
            }
            else -> null
        } ?: return listOf()
        val symbolMap = getGoToPbSymbolMap(pbFile)
        log.debug("pbFile=$pbFile, convertedName=$convertedName")
        return symbolMap[convertedName]?.let { listOf(it) } ?: emptyList()
    }

    companion object {

        /**
         * Converts to oneof field, message or service name.
         *
         *
         * If the type has exactly one method attached, and that method is isSomething, then it's
         * probably a oneof field, otherwise delegate to [convertToProtoMessageOrServiceName].
         */
        private fun convertTypeSpec(type: GoTypeSpec): QualifiedName? {
            val methods = type.allMethods
            if (methods.size == 1) {
                if (methods[0].name?.startsWith("is") == true) {
                    val qualifiedFieldName = underscoreToQualifiedName(type.name)
                            ?: return null
                    var fieldName = qualifiedFieldName.lastComponent
                            ?: return null
                    fieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)
                    return qualifiedFieldName.removeLastComponent()
                            .append(fieldName)
                }
            }
            return convertToProtoMessageOrServiceName(type)
        }

        /**
         * Converts to either message or service name.
         *
         *
         * FooBarClient or FooBarServer converts to service FooBar, everything else converts to
         * message.
         */
        private fun convertToProtoMessageOrServiceName(type: GoTypeSpec): QualifiedName? {
            val messageOrServiceName = underscoreToQualifiedName(type.name)
                    ?: return null
            return convertToServiceName(messageOrServiceName)
                    ?: messageOrServiceName
        }

        private fun underscoreToQualifiedName(name: String?): QualifiedName? {
            return name?.replace('_', '.')
                    ?.let { QualifiedName.fromDottedString(it) }
        }

        private fun convertToServiceName(serviceQualifiedName: QualifiedName): QualifiedName? {
            val serviceName = serviceQualifiedName.takeIf { it.componentCount == 1 }?.lastComponent
                    ?: return null
            for (suffix in listOf("Client", "Server", "ClientInterface")) {
                if (serviceName.endsWith(suffix)) {
                    return QualifiedName.fromComponents(serviceName.dropLast(suffix.length))
                }
            }
            return null
        }

        /**
         * Converts to either message field or service method name.
         *
         *
         * GetFooBar converts to field foo_bar, everything else converts to method.
         */
        private fun convertToProtoFieldOrMethodName(method: GoMethodDeclaration): QualifiedName? {
            val messageOrServiceName = Optional.of(method)
                    .map { obj: GoMethodDeclaration -> obj.receiverType }
                    .filter { obj: GoType? -> GoPointerType::class.java.isInstance(obj) }
                    .map { obj: GoType? -> GoPointerType::class.java.cast(obj) }
                    .map { obj: GoPointerType -> obj.type }
                    .map { obj -> obj?.contextlessResolve() }
                    .filter { obj: PsiElement? -> GoTypeSpec::class.java.isInstance(obj) }
                    .map { obj: PsiElement? -> GoTypeSpec::class.java.cast(obj) }
                    .map { type: GoTypeSpec -> convertToProtoMessageOrServiceName(type) }
                    .orElse(null)
            val methodName = method.name
            if (messageOrServiceName == null || methodName == null) {
                return null
            }
            val fieldPrefix = "Get"
            if (methodName.startsWith(fieldPrefix)) {
                val goFieldName = methodName.substring(fieldPrefix.length)
                val pbFieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, goFieldName)
                return messageOrServiceName.append(pbFieldName)
            }
            return messageOrServiceName.append(methodName)
        }

        /**
         * Interface method (which is spec instead of declaration) converts to service method in proto.
         */
        private fun convertToProtoServiceMethodName(method: GoMethodSpec): QualifiedName? {
            val serviceName = method.parentOfType<GoTypeSpec>()
                    ?.let { convertToProtoMessageOrServiceName(it) }
                    ?: return null
            val methodName = method.name ?: return null
            return serviceName.append(methodName)
        }

        private fun convertToProtoFieldName(field: GoFieldDefinition): QualifiedName? {
            val messageName = field.parentOfType<GoTypeSpec>()
                    ?.let { type: GoTypeSpec -> convertToProtoMessageOrServiceName(type) }
                    ?: return null
            val goFieldName = field.name ?: return null
            return messageName.append(goFieldName)
        }

        private fun convertToProtoEnumValueName(definition: GoConstDefinition): QualifiedName? {
            var enumTypeName = Optional.ofNullable(PsiTreeUtil.getParentOfType(definition, GoConstSpec::class.java))
                    .map { obj: GoConstSpec -> obj.type }
                    .map { obj -> obj?.contextlessResolve() }
                    .filter { obj: PsiElement? -> GoTypeSpec::class.java.isInstance(obj) }
                    .map { obj: PsiElement? -> GoTypeSpec::class.java.cast(obj) }
                    .map { type: GoTypeSpec -> convertToProtoMessageOrServiceName(type) }
                    .orElse(null)
            val valueName = definition.name
            if (enumTypeName == null || valueName == null) {
                return null
            }
            val prefix: String
            if (enumTypeName.componentCount == 1) {
                // If there's only one component, then the enum is top level
                // the go enum value will be prefixed by the enum type,
                // but the proto value will be directly under the proto package.
                prefix = enumTypeName.lastComponent + '_'
                enumTypeName = enumTypeName.removeLastComponent()
            } else if (enumTypeName.componentCount > 1) {
                // If there's more than one component then this is an enum nested under a message
                // the go enum value will be prefixed by the containing enum type (with parent message),
                // but the proto value will be under the containing message type (*without* the enum type).
                enumTypeName = enumTypeName.removeLastComponent()
                prefix = enumTypeName.join("_") + '_'
            } else {
                // Shouldn't happen.
                return null
            }
            return if (valueName.startsWith(prefix)) {
                enumTypeName.append(valueName.substring(prefix.length))
            } else null
        }

        /** NewFooClient is a standalone function to produce a client for the Foo service.  */
        private fun convertToProtoServiceName(function: GoFunctionDeclaration): QualifiedName? {
            val functionName = function.name ?: return null
            val prefix = "New"
            val suffix = "Client"
            if (functionName.startsWith(prefix) && functionName.endsWith(suffix)) {
                val serviceName = functionName.substring(prefix.length, functionName.lastIndexOf(suffix))
                return QualifiedName.fromComponents(serviceName)
            }
            return null
        }

    }
}