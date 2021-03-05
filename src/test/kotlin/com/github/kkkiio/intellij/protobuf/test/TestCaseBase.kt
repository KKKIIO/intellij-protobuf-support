package com.github.kkkiio.intellij.protobuf.test

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

abstract class TestCaseBase : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "testData/src"
    private val caseFileDir = "${javaClass.simpleName}/"

    protected fun configureCaseFile(filePath: String) {
        myFixture.configureByFile(filePath.caseFileFullPath())
    }

    protected fun checkResultByCaseFile(filePath: String,
                                        ignoreTrailingWhitespaces: Boolean = false) {
        myFixture.checkResultByFile(filePath.caseFileFullPath(), ignoreTrailingWhitespaces)
    }

    private fun String.caseFileFullPath() = caseFileDir + this


    private fun localFileUrl(relPath: String): String {
        return File(relPath).also { check(it.exists()) }
                .let { "file://" + it.absolutePath }
    }
}