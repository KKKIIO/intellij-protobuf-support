package com.github.kkkiio.intellij.protobuf.golang

import com.goide.go.WithScopeRestriction
import com.goide.psi.GoSpecType
import com.goide.psi.GoStructType
import com.goide.psi.GoTypeSpec
import com.goide.stubs.index.GoTypesIndex
import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.DefinitionsScopedSearch.SearchParameters
import com.intellij.psi.util.parentOfType
import com.intellij.util.Processor
import idea.plugin.protoeditor.lang.PbFileType
import idea.plugin.protoeditor.lang.PbLanguage
import idea.plugin.protoeditor.lang.psi.PbField
import idea.plugin.protoeditor.lang.psi.PbMessageDefinition
import org.jetbrains.annotations.NotNull


private val log: Logger = Logger.getInstance(PbGolangStructImplementationsSearch::class.java)

fun GlobalSearchScope.intersectIfNeeded(parameter: SearchParameters): GlobalSearchScope {
    val parameterScope = parameter.scope
    return if (parameter is WithScopeRestriction && parameterScope is GlobalSearchScope) intersectWith(parameterScope) else this
}

class PbGolangStructImplementationsSearch : QueryExecutorBase<PsiElement, SearchParameters>(true) {
    override fun processQuery(queryParameters: @NotNull SearchParameters,
                              consumer: @NotNull Processor<in PsiElement>) {
        // todo: figure out why processQuery called twice
        log.debug("processQuery start, queryParameters.element=${queryParameters.element}, consumer=$consumer")
        val pbElem = queryParameters.element.takeIf { it.language.`is`(PbLanguage.INSTANCE) }
                ?: return
        val (goStructTypeConsumer, pbMessageDefinition) = when (pbElem) {
            is PbField -> {
                val pbMessageDefinition = pbElem.parentOfType<PbMessageDefinition>()
                if (pbMessageDefinition == null) {
                    log.debug("pbElem.parentOfType<PbMessageDefinition>()=null, pbElem=$pbElem")
                    return
                }
                val pbFieldName = pbElem.name
                if (pbFieldName == null) {
                    log.debug("pbElem.name=null, pbElem=$pbElem")
                    return
                }
                val goFieldName = protoGenSpec.goCamelCase(pbFieldName)
                ({ _: GoSpecType, structType: GoStructType ->
                    consumer.process(structType.fieldDefinitions.first { it.name == goFieldName })
                }) to pbMessageDefinition
            }
            is PbMessageDefinition -> {
                ({ specType: GoSpecType, _: GoStructType -> consumer.process(specType) }) to pbElem
            }
            else -> return
        }

        log.debug("pbMessageDefinition={name=${pbMessageDefinition.name}, body=${pbMessageDefinition.body}}")
        val messageName = pbMessageDefinition.name ?: return
        val goStructName = protoGenSpec.goCamelCase(messageName)
        val filter = pbMessageDefinition.containingFile.name.let {
            val ext = "." + PbFileType.INSTANCE.defaultExtension
            if (it.endsWith(ext)) {
                getGenPbGoFileFilter(queryParameters.project, it.dropLast(ext.length))
            } else {
                getAllPbGoFilesFilter(queryParameters.project)
            }
        }
        log.debug("GoTypesIndex.process start, name=$goStructName, filter=$filter")
        GoTypesIndex.process(
                goStructName,
                queryParameters.project,
                GlobalSearchScope.projectScope(queryParameters.project),
                filter
        ) { typeSpec: GoTypeSpec ->
            val identifier = typeSpec.identifier
            val specType = typeSpec.specType
            log.debug("index process start, typeSpec={name=${typeSpec.name}, isTypeAlias=${typeSpec.isTypeAlias}, shouldGoDeeper=${typeSpec.shouldGoDeeper()}}, identifier={text=${identifier.text}}, specType={type=${specType.type}, identifier=${specType.identifier}}")
            (specType.type as? GoStructType)?.let { structType ->
                goStructTypeConsumer(specType, structType)
            } ?: true
        }
    }
}