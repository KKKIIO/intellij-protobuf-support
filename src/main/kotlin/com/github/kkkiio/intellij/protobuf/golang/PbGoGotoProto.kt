package com.github.kkkiio.intellij.protobuf.golang

import com.github.kkkiio.intellij.protobuf.utils.repr
import com.goide.psi.*
import com.google.common.base.CaseFormat
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.debug
import com.intellij.psi.PsiElement
import com.intellij.psi.util.*
import idea.plugin.protoeditor.lang.psi.*
import java.util.*

/**
 * Reference: https://developers.google.com/protocol-buffers/docs/reference/go-generated
 */
@ExperimentalStdlibApi
object PbGoGotoProto {
    private val log: Logger = Logger.getInstance(PbGoGotoProto::class.java)

    /**
     * Converts to oneof field, message or service name.
     *
     *
     * If the type has exactly one method attached, and that method is isSomething, then it's
     * probably a oneof field, otherwise delegate to [convertToProtoMessageOrServiceName].
     */
    fun convertTypeSpec(type: GoTypeSpec): QualifiedName? {
        val methods = type.allMethods
        if (methods.size == 1) {
            if (methods[0].name?.startsWith("is") == true) {
                val qualifiedFieldName =
                    underscoreToQualifiedName(type.name)
                        ?: return null
                var fieldName = qualifiedFieldName.lastComponent
                    ?: return null
                fieldName = CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_UNDERSCORE,
                    fieldName
                )
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
        val serviceName =
            serviceQualifiedName.takeIf { it.componentCount == 1 }?.lastComponent
                ?: return null
        for (suffix in listOf("Client", "Server", "ClientInterface")) {
            if (serviceName.endsWith(suffix)) {
                return QualifiedName.fromComponents(
                    serviceName.dropLast(
                        suffix.length
                    )
                )
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
    fun convertToProtoFieldOrMethodName(method: GoMethodDeclaration): QualifiedName? {
        val messageOrServiceName = Optional.of(method)
            .map { obj: GoMethodDeclaration -> obj.receiverType }
            .filter { obj: GoType? ->
                GoPointerType::class.java.isInstance(
                    obj
                )
            }
            .map { obj: GoType? -> GoPointerType::class.java.cast(obj) }
            .map { obj: GoPointerType -> obj.type }
            .map { obj -> obj?.contextlessResolve() }
            .filter { obj: PsiElement? ->
                GoTypeSpec::class.java.isInstance(
                    obj
                )
            }
            .map { obj: PsiElement? -> GoTypeSpec::class.java.cast(obj) }
            .map { type: GoTypeSpec ->
                convertToProtoMessageOrServiceName(
                    type
                )
            }
            .orElse(null)
        val methodName = method.name
        if (messageOrServiceName == null || methodName == null) {
            return null
        }
        val fieldPrefix = "Get"
        if (methodName.startsWith(fieldPrefix)) {
            val goFieldName = methodName.substring(fieldPrefix.length)
            val pbFieldName = CaseFormat.UPPER_CAMEL.to(
                CaseFormat.LOWER_UNDERSCORE,
                goFieldName
            )
            return messageOrServiceName.append(pbFieldName)
        }
        return messageOrServiceName.append(methodName)
    }

    /**
     * Interface method (which is spec instead of declaration) converts to service method in proto.
     */
    fun convertToProtoServiceMethodName(method: GoMethodSpec): QualifiedName? {
        val serviceName = method.parentOfType<GoTypeSpec>()
            ?.let { convertToProtoMessageOrServiceName(it) }
            ?: return null
        val methodName = method.name ?: return null
        return serviceName.append(methodName)
    }

    fun convertToProtoFieldName(field: GoFieldDefinition): QualifiedName? {
        val messageName = field.parentOfType<GoTypeSpec>()
            ?.let { type: GoTypeSpec ->
                convertToProtoMessageOrServiceName(
                    type
                )
            }
            ?: return null
        val goFieldName = field.name ?: return null
        return messageName.append(goFieldName)
    }

    fun convertToProtoEnumValueName(definition: GoConstDefinition): QualifiedName? {
        var enumTypeName = Optional.ofNullable(
            PsiTreeUtil.getParentOfType(
                definition,
                GoConstSpec::class.java
            )
        )
            .map { obj: GoConstSpec -> obj.type }
            .map { obj -> obj?.contextlessResolve() }
            .filter { obj: PsiElement? ->
                GoTypeSpec::class.java.isInstance(
                    obj
                )
            }
            .map { obj: PsiElement? -> GoTypeSpec::class.java.cast(obj) }
            .map { type: GoTypeSpec ->
                convertToProtoMessageOrServiceName(
                    type
                )
            }
            .orElse(null)
        val valueName = definition.name
        if (enumTypeName == null || valueName == null) {
            return null
        }
        val prefix: String
        when {
            enumTypeName.componentCount == 1 -> {
                // If there's only one component, then the enum is top level
                // the go enum value will be prefixed by the enum type,
                // but the proto value will be directly under the proto package.
                prefix = enumTypeName.lastComponent + '_'
                enumTypeName = enumTypeName.removeLastComponent()
            }
            enumTypeName.componentCount > 1 -> {
                // If there's more than one component then this is an enum nested under a message
                // the go enum value will be prefixed by the containing enum type (with parent message),
                // but the proto value will be under the containing message type (*without* the enum type).
                enumTypeName = enumTypeName.removeLastComponent()
                prefix = enumTypeName.join("_") + '_'
            }
            else -> {
                // Shouldn't happen.
                return null
            }
        }
        return if (valueName.startsWith(prefix)) {
            enumTypeName.append(valueName.substring(prefix.length))
        } else null
    }

    /** NewFooClient is a standalone function to produce a client for the Foo service.  */
    fun convertToProtoServiceName(function: GoFunctionDeclaration): QualifiedName? {
        val functionName = function.name ?: return null
        val prefix = "New"
        val suffix = "Client"
        if (functionName.startsWith(prefix) && functionName.endsWith(suffix)) {
            val serviceName = functionName.substring(
                prefix.length,
                functionName.lastIndexOf(suffix)
            )
            return QualifiedName.fromComponents(serviceName)
        }
        return null
    }


    fun getPbGoSymbolMap(pbFile: PbFile): Map<QualifiedName, PbSymbol> = CachedValuesManager.getCachedValue(pbFile) {
        CachedValueProvider.Result.create(computePbGoSymbolMap(pbFile), PsiModificationTracker.MODIFICATION_COUNT)
    }

    fun computePbGoSymbolMap(pbFile: PbFile): Map<QualifiedName, PbSymbol> {
        val map = buildMap<QualifiedName, PbSymbol> {
            fun addEnum(enum: PbEnumDefinition, messageGoName: String?) {
                val enumQualifiedName = enum.qualifiedName ?: return
                val enumGoName = PbGoGenSpec.newGoIdent(
                    enumQualifiedName,
                    pbFile.packageQualifiedName
                )
                putAndWarnConflict(QualifiedName.fromComponents(enumGoName), enum)
                enum.enumValues.forEach { symbol ->
                    val symbolName = symbol.name ?: return@forEach
                    putAndWarnConflict(
                        QualifiedName.fromComponents(
                            PbGoGenSpec.enumValueName(
                                messageGoName,
                                enumGoName,
                                symbolName
                            )
                        ), symbol
                    )
                }
            }

            fun addMsg(msg: PbMessageDefinition, msgName: String) {
                val msgGoName = PbGoGenSpec.goCamelCase(msgName)
                putAndWarnConflict(QualifiedName.fromComponents(msgGoName), msg)
                val messageBody = msg.body ?: return
                messageBody.simpleFieldList.forEach { pbSimpleField ->
                    pbSimpleField.name?.let { symbolName ->
                        putAndWarnConflict(
                            QualifiedName.fromComponents(msgGoName, PbGoGenSpec.goCamelCase(symbolName)),
                            pbSimpleField
                        )
                    }
                }

                messageBody.enumDefinitionList.forEach { pbEnumDefinition ->
                    addEnum(pbEnumDefinition, msgGoName)
                }
                // todo: handle more case
            }

            fun addSymbolsRecursively(symbolOwner: PbSymbolOwner) {
                symbolOwner.symbols.forEach { symbol ->
                    val symbolName = symbol.name ?: return@forEach
                    when (symbol) {
                        is PbMessageDefinition -> addMsg(symbol, symbolName)
                        is PbEnumDefinition -> addEnum(symbol, null)
                        // todo: handle more case
                        is PbSymbolOwner ->
                            addSymbolsRecursively(symbol as PbSymbolOwner)
                    }
                }
            }
            addSymbolsRecursively(pbFile)
        }
        log.debug { "computeLocalSymbolMap finished, pbFile.name=${pbFile.name}, map.size=${map.size}" }
        return map
    }

    private fun MutableMap<QualifiedName, PbSymbol>.putAndWarnConflict(goName: QualifiedName, pbSymbol: PbSymbol) {
        val old = put(goName, pbSymbol)
        if (old != null) {
            log.warn("conflict symbols with same goName=$goName, old=${old.repr()}, new=${pbSymbol.repr()}")
        }
    }
}