package com.github.kkkiio.intellij.protobuf.golang

import com.goide.intentions.expressions.structLiteral.GoFillFieldsIntentionBase
import com.goide.intentions.expressions.structLiteral.GoFillStructInfo
import com.goide.intentions.expressions.structLiteral.GoFillStructOptions
import com.goide.psi.GoNamedElement
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import idea.plugin.protoeditor.ide.PbIdeBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nls.Capitalization
import java.util.function.Predicate

class PbGoFillAllFieldsIntention : GoFillFieldsIntentionBase((GoFillStructOptions.build() as Any)
        .let {
            val clazz = it.javaClass
            clazz.getDeclaredMethod("setAvailableOnEmptyLiteral", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, true)
            clazz.getDeclaredMethod("setAvailableWhenAllFieldsFilled", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, false)
            clazz.getDeclaredMethod("setEnforceMultiline", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, true)
            clazz.getDeclaredMethod("setRecursiveMode", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, false)
            clazz.getDeclaredMethod("setRemoveKeys", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, false)
            clazz.getDeclaredMethod("setInteractive", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, true)
            clazz.getDeclaredMethod("setSelectFields", Boolean::class.java)
                    .also { it.isAccessible = true }
                    .invoke(it, false)
            clazz.getDeclaredMethod("getOptions")
                    .also { it.isAccessible = true }
                    .invoke(it) as GoFillStructOptions
        }) {
    override fun getText(): @Nls(capitalization = Capitalization.Sentence) String {
        return familyName
    }

    override fun getFamilyName(): @Nls String {
        return PbIdeBundle.message("pb.go.intention.fill.all.fields.name")
    }

    private val myOptionsF = GoFillFieldsIntentionBase::class.java.getDeclaredField("myOptions")
            .also { it.isAccessible = true }


    private val handlerClazz = Class.forName("com.goide.intentions.expressions.structLiteral.GoFillStructHandler")

    val handlerConstructor = handlerClazz
            .getDeclaredConstructor(
                    Boolean::class.java,
                    Boolean::class.java,
                    Boolean::class.java,
                    Boolean::class.java
            )
            .also { it.isAccessible = true }
    private val handlerFillMethod = handlerClazz.getDeclaredMethod("fill", Project::class.java, Editor::class.java, GoFillStructInfo::class.java, Predicate::class.java)
            .also { it.isAccessible = true }

    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project,
                        editor: Editor?,
                        element: PsiElement) {
        if (editor != null) {
            val options = myOptionsF.get(this) as GoFillStructOptions
            val structInfo = GoFillStructInfo.get(element, options.isAvailableOnEmptyLiteral, options.isAvailableWhenAllFieldsFilled, false)
            if (structInfo != null) {
                val literalIsMultiline = structInfo.literalValue.textContains('\n')
                if (!options.isSelectFields) {
                    val handler = handlerConstructor
                            .newInstance(options.isRecursiveMode, options.isEnforceMultiline || literalIsMultiline, options.isRemoveKeys, options.isInteractive)
                    handlerFillMethod.invoke(handler, project, editor, structInfo, Predicate<GoNamedElement> {
                        it.name?.startsWith("XXX_") != true
                    })
                    return
                }
            }
        }
        super.invoke(project, editor, element)
    }
}