package com.github.kkkiio.intellij.protobuf.proto.intentions

import com.github.kkkiio.intellij.protobuf.test.TestCaseBase

class SequencingPbFieldNumIntentionTest : TestCaseBase() {
    fun testSimple() = doTest()

    private fun doTest() {
        val testName: String = getTestName(true)
        configureCaseFile("$testName.before.proto")
        val intention = myFixture.findSingleIntention("Sequencing")
        myFixture.launchAction(intention)
        val after = "$testName.after.proto"
        checkResultByCaseFile(after)
    }
}