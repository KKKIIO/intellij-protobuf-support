package com.github.kkkiio.intellij.protobuf.test

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess
import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import idea.plugin.protoeditor.lang.PbFileType
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
        registerTestDataFileExtension()
    }

    override fun tearDown() {
        Disposer.dispose(testDisposable)
        super.tearDown()
    }


    protected fun configureCaseFile(filePath: String): PsiFile =
        myFixture.configureByFile(filePath.caseFileFullPath())

    protected fun checkResultByCaseFile(
        filePath: String,
        ignoreTrailingWhitespaces: Boolean = false
    ) {
        myFixture.checkResultByFile(filePath.caseFileFullPath(), ignoreTrailingWhitespaces)
    }

    fun String.caseFileFullPath() = caseFileDir + this

    open fun registerTestDataFileExtension() = ApplicationManager.getApplication().runWriteAction {
        FileTypeManager.getInstance().associatePattern(PbFileType.INSTANCE, "*.proto.testdata")
    }
}