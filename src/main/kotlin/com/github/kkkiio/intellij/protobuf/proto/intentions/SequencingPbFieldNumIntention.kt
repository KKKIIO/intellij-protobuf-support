package com.github.kkkiio.intellij.protobuf.proto.intentions

import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.intention.BaseElementAtCaretIntentionAction
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfType
import idea.plugin.protoeditor.lang.PbLanguage
import idea.plugin.protoeditor.lang.psi.PbField
import idea.plugin.protoeditor.lang.psi.PbTypes
import idea.plugin.protoeditor.lang.psi.ProtoLeafElement
import idea.plugin.protoeditor.lang.psi.impl.PbElementFactory

class HintUserException(msg: String,
                        val hintMsg: String = msg) : RuntimeException(msg)

class SequencingPbFieldNumIntention : BaseElementAtCaretIntentionAction() {
    override fun getFamilyName(): String {
        return "SequencingPbFieldNum"
    }

    override fun getText(): String {
        return "Sequencing fields number"
    }

    private val log = logger<SequencingPbFieldNumIntention>()

    override fun isAvailable(project: Project,
                             editor: Editor?,
                             element: PsiElement): Boolean {
        return element.language.`is`(PbLanguage.INSTANCE) && element.parentOfType<PbField>(withSelf = true) != null
    }

    override fun invoke(project: Project,
                        editor: Editor,
                        element: PsiElement) {
        try {
            val startField = element.parentOfType<PbField>(withSelf = true)
                    ?: return
            log.debug { "startField=${startField.text}" }
            val prevField = PsiTreeUtil.getPrevSiblingOfType(startField, PbField::class.java)
            val startNum = if (prevField != null) {
                val prevNum = prevField.fieldNumber?.longValue
                        ?: throw HintUserException("previous field missing number")
                prevNum + 1
            } else {
                1
            }

            val factory = PbElementFactory.getInstance(startField.pbFile)
            generateSequence<PsiElement>(startField) { it.nextSibling }.filterIsInstance<PbField>()
                    .mapTo(arrayListOf<(Int) -> Unit>()) {
                        val fieldNumber = it.fieldNumber
                        if (fieldNumber != null) {
                            { offset ->
                                fieldNumber.replace(factory.parseLight(PbTypes.NUMBER_VALUE, (startNum + offset).toString()))
                            }
                        } else {
                            val eqSymbol = PsiTreeUtil.getNextSiblingOfType(it.nameIdentifier, ProtoLeafElement::class.java)
                            if (eqSymbol == null || eqSymbol.text != "=") {
                                throw HintUserException("can't find '=' in field='${it.text}'")
                            }
                            { offset ->
                                it.addAfter(factory.parseLight(PbTypes.NUMBER_VALUE, (startNum + offset).toString()), eqSymbol)
                            }
                        }
                    }.forEachIndexed { offset, action ->
                        action(offset)
                    }
        } catch (e: HintUserException) {
            HintManager.getInstance()
                    .showInformationHint(editor, e.hintMsg)
        }
    }
}