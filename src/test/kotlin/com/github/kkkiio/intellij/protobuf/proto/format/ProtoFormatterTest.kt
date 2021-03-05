package com.github.kkkiio.intellij.protobuf.proto.format

import com.github.kkkiio.intellij.protobuf.test.TestCaseBase
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager

class ProtoFormatterTest : TestCaseBase() {
    fun testSimple() {
        doTest()
    }

    private fun doTest(c: Char? = null) {
        val testName: String = getTestName(true)
        configureCaseFile("$testName.before.proto")
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

}