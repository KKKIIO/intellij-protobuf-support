package com.github.kkkiio.intellij.protobuf.golang

import com.github.kkkiio.intellij.protobuf.test.TestCaseBase
import com.intellij.psi.util.QualifiedName
import idea.plugin.protoeditor.lang.psi.PbFile

class PbGoGotoProtoTest : TestCaseBase() {

    @ExperimentalStdlibApi
    fun testComputePbGoSymbolMap() {
        val testName: String = getTestName(true)
        val file = configureCaseFile("$testName.proto")
        val pbFile = assertInstanceOf(file, PbFile::class.java)
        val symbolMap = PbGoGotoProto.computePbGoSymbolMap(pbFile)
        assertSameElements(
            symbolMap.keys,
            setOf(
                "ExampleMessage",
                "ExampleMessage.Id",
                "ExampleMessage.Status",
                "ExampleMessage.Type",
                "ExampleMessage.Data",
                "ExampleMessage_Status",
                "ExampleMessage_INACTIVE",
                "ExampleMessage_ACTIVE",
                "ExampleMessagePtype",
                "ExampleMessage_P0",
                "ExampleMessage_P1"
            ).map { QualifiedName.fromDottedString(it) }
        )
    }
}