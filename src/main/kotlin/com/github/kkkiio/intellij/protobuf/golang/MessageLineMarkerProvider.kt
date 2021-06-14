package com.github.kkkiio.intellij.protobuf.golang

import com.github.kkkiio.intellij.protobuf.utils.repr
import com.goide.GoTypes
import com.goide.psi.GoTypeSpec
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.logger
import com.intellij.psi.PsiElement
import com.intellij.psi.util.QualifiedName
import com.intellij.psi.util.elementType
import idea.plugin.protoeditor.gencode.ProtoFromSourceComments
import idea.plugin.protoeditor.ide.util.PbIcons
import idea.plugin.protoeditor.lang.psi.PbEnumDefinition

@ExperimentalStdlibApi
private val log: Logger = logger<MessageLineMarkerProvider>()

@ExperimentalStdlibApi
class MessageLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<PsiElement>>
    ) {
        val typeSpec = element.takeIf { it.elementType === GoTypes.IDENTIFIER }?.parent?.parent as? GoTypeSpec ?: return
        val pbGoFile = (element.containingFile)?.takeIf {
            it.virtualFile?.name?.endsWith(".pb.go") ?: false
        } ?: return
        val pbFile = ProtoFromSourceComments.findProtoOfGeneratedCode("//", pbGoFile)
        if (pbFile == null) {
            log.info("can't find corresponding proto file for ${pbGoFile.name} file")
            return
        }

        val name = typeSpec.name
        log.debug("pbFile.name=${pbFile.name}, typeSpec=${typeSpec.repr()}, typeSpec.name=$name")
        val symbolMap = PbGoGotoProto.getPbGoSymbolMap(pbFile)
        val pbSymbol = symbolMap[QualifiedName.fromComponents(name)] ?: return
        val icon = if (pbSymbol is PbEnumDefinition) {
            PbIcons.ENUM
        } else {
            PbIcons.MESSAGE
        }
        result.add(
            NavigationGutterIconBuilder.create(icon)
                .setTargets(pbSymbol.nameIdentifier)
                .setTooltipText("Navigate to proto declaration")
                .createLineMarkerInfo(element)
        )
    }
}