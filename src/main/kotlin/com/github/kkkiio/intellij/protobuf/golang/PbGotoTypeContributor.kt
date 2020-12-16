package com.github.kkkiio.intellij.protobuf.golang

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.GotoClassContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.Processor
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import idea.plugin.protoeditor.lang.psi.PbNamedElement
import idea.plugin.protoeditor.lang.stub.index.QualifiedNameIndex

class PbGotoTypeContributor : GotoClassContributor, ChooseByNameContributorEx {
    private val stubIndexKey = QualifiedNameIndex.KEY

    override fun getNames(project: Project,
                          includeNonProjectItems: Boolean): Array<String> {
        return StubIndex.getInstance()
                .getAllKeys(stubIndexKey, project)
                .toTypedArray()
    }

    override fun getItemsByName(name: String,
                                pattern: String,
                                project: Project,
                                includeNonProjectItems: Boolean): Array<NavigationItem> {
        val scope = if (includeNonProjectItems) GlobalSearchScope.allScope(project) else GlobalSearchScope.projectScope(project)
        return StubIndex.getElements(stubIndexKey, name, project, scope, PbNamedElement::class.java)
                .toTypedArray()
    }

    override fun processNames(processor: Processor<in String>,
                              scope: GlobalSearchScope,
                              filter: IdFilter?) {
        StubIndex.getInstance()
                .processAllKeys(stubIndexKey, processor, scope, filter)
    }

    override fun processElementsWithName(name: String,
                                         processor: Processor<in NavigationItem>,
                                         parameters: FindSymbolParameters) {
        StubIndex.getInstance()
                .processElements(stubIndexKey, name, parameters.project, parameters.searchScope, parameters.idFilter, PbNamedElement::class.java, processor)
    }


    override fun getQualifiedName(item: NavigationItem): String? {
        return (item as? PbNamedElement)?.qualifiedName?.join(qualifiedNameSeparator)
    }

    override fun getQualifiedNameSeparator(): String {
        return "."
    }
}