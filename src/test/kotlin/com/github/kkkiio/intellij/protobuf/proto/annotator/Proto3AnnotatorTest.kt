package com.github.kkkiio.intellij.protobuf.proto.annotator

import com.github.kkkiio.intellij.protobuf.test.TestCaseBase


class Proto3AnnotatorTest : TestCaseBase() {
    fun testSimple() {
        configureCaseFile("import1.proto")
        configureCaseFile("proto2enum.proto")
        myFixture.testHighlighting(false, false, false, "Proto3Errors.proto.testdata".caseFileFullPath())
    }
}