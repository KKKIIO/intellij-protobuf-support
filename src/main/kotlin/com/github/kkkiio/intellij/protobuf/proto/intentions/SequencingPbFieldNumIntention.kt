package com.github.kkkiio.intellij.protobuf.proto.intentions

import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.intention.BaseElementAtCaretIntentionAction
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfTypes
import idea.plugin.protoeditor.lang.PbLanguage
import idea.plugin.protoeditor.lang.psi.*
import idea.plugin.protoeditor.lang.psi.impl.PbElementFactory


class SequencingPbFieldNumIntention : BaseElementAtCaretIntentionAction() {
    override fun getFamilyName(): String {
        return "SequencingPbFieldNum"
    }

    override fun getText(): String {
        return "Sequencing fields number"
    }

    private val log = logger<SequencingPbFieldNumIntention>()

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        return element.language.`is`(PbLanguage.INSTANCE) && element.parentOfTypes(
            withSelf = true,
            classes = *arrayOf(PbField::class, PbEnumValue::class)
        ) != null
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        log.debug { "invoke start, element=${element.text}" }
        val (startField: SeqField, defaultNumber: Long) =
            element.parentOfTypes(withSelf = true, classes = *arrayOf(PbField::class, PbEnumValue::class))?.let {
                when (it) {
                    is PbField -> Field(it) to 1L
                    is PbEnumValue -> EnumValue(it) to 0L
                    else -> null
                }
            } ?: return
        val prevField = startField.prevField
        val startNum = if (prevField == null) {
            defaultNumber
        } else {
            val prevNum = prevField.fieldNumber?.longValue
            if (prevNum != null) {
                prevNum + 1
            } else {
                HintManager.getInstance().showInformationHint(editor, "previous field missing number")
                return
            }
        }

        val factory = PbElementFactory.getInstance(element.containingFile)
        generateSequence(startField) { it.nextField }
            .mapTo(arrayListOf<(Int) -> Unit>()) {
                val fieldNumber = it.fieldNumber
                if (fieldNumber != null) {
                    { offset ->
                        fieldNumber.replace(factory.parseLight(PbTypes.NUMBER_VALUE, (startNum + offset).toString()))
                    }
                } else {
                    val eqSymbol = PsiTreeUtil.getNextSiblingOfType(it.nameIdentifier, ProtoLeafElement::class.java)
                    if (eqSymbol == null || eqSymbol.text != "=") {
                        HintManager.getInstance().showInformationHint(editor, "can't find '=' in field='${it.text}'")
                        return
                    }
                    { offset ->
                        it.addAfter(factory.parseLight(PbTypes.NUMBER_VALUE, (startNum + offset).toString()), eqSymbol)
                    }
                }
            }.forEachIndexed { offset, action ->
                action(offset)
            }
    }
}

private interface SeqField : PbNamedElement {
    val prevField: SeqField?
    val nextField: SeqField?
    val fieldNumber: PbNumberValue?
}

private class Field(val pbField: PbField) : PbNamedElement by pbField, SeqField {
    override val prevField: SeqField?
        get() = PsiTreeUtil.getPrevSiblingOfType(pbField, PbField::class.java)?.let { Field(it) }

    override val nextField: SeqField?
        get() = PsiTreeUtil.getNextSiblingOfType(pbField, PbField::class.java)?.let { Field(it) }

    override val fieldNumber: PbNumberValue? get() = pbField.fieldNumber
}

private class EnumValue(val pbEnumValue: PbEnumValue) : PbNamedElement by pbEnumValue, SeqField {
    override val prevField: SeqField?
        get() = PsiTreeUtil.getPrevSiblingOfType(pbEnumValue, PbEnumValue::class.java)?.let { EnumValue(it) }

    override val nextField: SeqField?
        get() = PsiTreeUtil.getNextSiblingOfType(pbEnumValue, pbEnumValue::class.java)?.let { EnumValue(it) }

    override val fieldNumber: PbNumberValue? get() = pbEnumValue.numberValue
}