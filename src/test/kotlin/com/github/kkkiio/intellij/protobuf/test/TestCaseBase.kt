package com.github.kkkiio.intellij.protobuf.test

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.nio.file.Paths


abstract class TestCaseBase : BasePlatformTestCase() {
    private val testDisposable: Disposable = TestDisposable()

    override fun getTestDataPath(): String = "testData/src"
    private val caseFileDir = "${javaClass.simpleName}/"

    override fun setUp() {
        javaClass.getResource("/include")?.let {
            val resPath = Paths.get(it.toURI())
                    .toAbsolutePath()
                    .toString()
            VfsRootAccess.allowRootAccess(testDisposable, resPath)
        }
        super.setUp()
    }

    override fun tearDown() {
        Disposer.dispose(testDisposable)
        super.tearDown()
    }


    protected fun configureCaseFile(filePath: String) {
        myFixture.configureByFile(filePath.caseFileFullPath())
    }

    protected fun checkResultByCaseFile(filePath: String,
                                        ignoreTrailingWhitespaces: Boolean = false) {
        myFixture.checkResultByFile(filePath.caseFileFullPath(), ignoreTrailingWhitespaces)
    }

    private fun String.caseFileFullPath() = caseFileDir + this

}