package com.github.kkkiio.intellij.protobuf.proto.format

import com.github.kkkiio.intellij.protobuf.test.TestCaseBase
import com.intellij.application.options.CodeStyle
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import idea.plugin.protoeditor.lang.PbLanguage

class ProtoFormatterTest : TestCaseBase() {
    fun testSimple() {
        configAndTest {
            getCommonSettings().ALIGN_GROUP_FIELD_DECLARATIONS = true
        }
    }

    fun testQualifiedName() {
        configAndTest {
            getCommonSettings().ALIGN_GROUP_FIELD_DECLARATIONS = true
        }
    }

    fun testRpc() {
        configAndTest {
            getCommonSettings().ALIGN_GROUP_FIELD_DECLARATIONS = true
        }
    }

    fun testEnum() {
        configAndTest {
            getCommonSettings().ALIGN_GROUP_FIELD_DECLARATIONS = true
        }
    }

    private fun configAndTest(c: Char? = null,
                              configureOptions: () -> Unit = {}) {
        val testName: String = getTestName(true)
        configureCaseFile("$testName.before.proto")
        configureOptions()
        if (c == null) {
            WriteCommandAction.runWriteCommandAction(myFixture.project) {
                CodeStyleManager.getInstance(project)
                        .reformat(myFixture.file)
            }
        } else {
            myFixture.type(c)
        }
        val after = "$testName.after.proto"
        checkResultByCaseFile(after)
    }

    private fun getCommonSettings(): CommonCodeStyleSettings =
            CodeStyle.getLanguageSettings(myFixture.file, PbLanguage.INSTANCE)

}