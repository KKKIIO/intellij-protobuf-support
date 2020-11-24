package com.github.kkkiio.intellij.protobuf.support.golang

import com.goide.go.WithScopeRestriction
import com.goide.psi.GoSpecType
import com.goide.psi.GoStructType
import com.goide.psi.GoTypeSpec
import com.goide.stubs.index.GoTypesIndex
import com.google.common.base.CaseFormat
import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.DefinitionsScopedSearch.SearchParameters
import com.intellij.util.Processor
import idea.plugin.protoeditor.lang.PbLanguage
import idea.plugin.protoeditor.lang.psi.PbMessageDefinition
import org.jetbrains.annotations.NotNull


private val log: Logger = Logger.getInstance(PbGolangStructImplementationsSearch::class.java)


fun GlobalSearchScope.intersectIfNeeded(parameter: SearchParameters): GlobalSearchScope {
    val parameterScope = parameter.scope
    return if (parameter is WithScopeRestriction && parameterScope is GlobalSearchScope) intersectWith(parameterScope) else this
}

class PbGolangStructImplementationsSearch : QueryExecutorBase<GoSpecType, SearchParameters>(true) {
    override fun processQuery(queryParameters: @NotNull SearchParameters,
                              consumer: @NotNull Processor<in GoSpecType>) {
        // todo: figure out why processQuery called twice
        log.info("processQuery start, queryParameters.element=${queryParameters.element}, consumer=$consumer")
        val pbMessageDefinition = queryParameters.element.takeIf { it.language.`is`(PbLanguage.INSTANCE) } as? PbMessageDefinition
                ?: return
        log.info("pbMessageDefinition={name=${pbMessageDefinition.name}, body=${pbMessageDefinition.body}}")
        val messageName = pbMessageDefinition.name ?: return


        // todo: search with case-insensitive option
        val key = guessCaseFormat(messageName)?.to(CaseFormat.UPPER_CAMEL, messageName)
                ?: messageName
        log.info("GoTypesIndex.process start, key=$key")
        GoTypesIndex.process(
                key,
                queryParameters.project,
                GlobalSearchScope.projectScope(queryParameters.project), null) { typeSpec: GoTypeSpec ->
            val identifier = typeSpec.identifier
            val specType = typeSpec.specType
            log.info("index process start, typeSpec={name=${typeSpec.name}, isTypeAlias=${typeSpec.isTypeAlias}}, shouldGoDeeper=${typeSpec.shouldGoDeeper()}, identifier={text=${identifier.text}}, specType={type=${specType.type}, identifier=${specType.identifier}}")
            specType.takeIf { it.type is GoStructType }?.let {
                consumer.process(it)
            } ?: true
        }
    }

    private val lowerCamelRegex = "([a-z]+[A-Z]+\\w+)+".toRegex()
    private val upperCamelRegex = "([A-Z]+[a-z]+\\w+)+".toRegex()

    private fun guessCaseFormat(s: String): CaseFormat? {
        if (s.contains("_")) {
            if (s.toUpperCase() == s) return CaseFormat.UPPER_UNDERSCORE
            if (s.toLowerCase() == s) return CaseFormat.LOWER_UNDERSCORE
        } else if (s.contains("-")) {
            if (s.toLowerCase() == s) return CaseFormat.LOWER_HYPHEN
        } else {
            if (Character.isLowerCase(s[0])) {
                if (s.matches(lowerCamelRegex)) return CaseFormat.LOWER_CAMEL
            } else {
                if (s.matches(upperCamelRegex)) return CaseFormat.UPPER_CAMEL
            }
        }
        return null
    }
}