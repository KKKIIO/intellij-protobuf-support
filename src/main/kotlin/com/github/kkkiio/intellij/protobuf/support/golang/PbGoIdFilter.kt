package com.github.kkkiio.intellij.protobuf.support.golang

import com.goide.GoFileType
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.VirtualFileWithId
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.util.SystemProperties
import com.intellij.util.concurrency.NonUrgentExecutor
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.IdFilter
import java.util.concurrent.*


private val log: Logger = Logger.getInstance(PbGolangStructImplementationsSearch::class.java)

private val ID_FILTER_CALCULATION_TIMEOUT_MS = SystemProperties.getIntProperty("goland.id.filter.timeout.ms", 50)
private val PB_GO_FILTER: Key<CachedValue<Future<Map<Int, String>>>> = Key.create("ALL_PB_GO")

fun getPbGoIdFilter(project: Project): Map<Int, String>? {
    val fileTypeRegistry = FileTypeRegistry.getInstance()
    val filterCondition = { file: VirtualFile -> fileTypeRegistry.isFileOfType(file, GoFileType.INSTANCE) && file.name.endsWith(".pb.go") }
    val key = PB_GO_FILTER
    val future = CachedValuesManager.getManager(project)
            .getCachedValue(project, key, {
                val task = FutureTask<Map<Int, String>> {
                    val idToFnMap = mutableMapOf<Int, String>()
                    FileBasedIndex.getInstance()
                            .iterateIndexableFiles({ fileOrDir: VirtualFile ->
                                if (filterCondition(fileOrDir) && fileOrDir is VirtualFileWithId && fileOrDir.id >= 0) {
                                    idToFnMap[fileOrDir.id] = fileOrDir.name
                                }
                                ProgressManager.checkCanceled()
                                true
                            }, project, null)
                    idToFnMap
                }
                NonUrgentExecutor.getInstance().execute(task)
                CachedValueProvider.Result.create(task, ProjectRootManager.getInstance(project), VirtualFileManager.VFS_STRUCTURE_MODIFICATIONS)
            }, false)
    return try {
        val idToFnMap = future.get(ID_FILTER_CALCULATION_TIMEOUT_MS.toLong(), TimeUnit.MILLISECONDS)
        log.debug("IdFilter[$key]. Cache hit.")
        idToFnMap
    } catch (e: ExecutionException) {
        log.debug("IdFilter[$key]. Cache miss. Filter will be calculated later")
        null
    } catch (e: TimeoutException) {
        log.debug("IdFilter[$key]. Cache miss. Filter will be calculated later")
        null
    } catch (e: InterruptedException) {
        log.debug("IdFilter[$key]. Cache miss. Filter will be calculated later")
        null
    }
}

fun getAllPbGoFilesFilter(project: Project): IdFilter? {
    return getPbGoIdFilter(project)?.let { PbGoIdFilter(it) }
}

fun getGenPbGoFileFilter(project: Project,
                         protoFileBaseName: String): IdFilter? {
    return getPbGoIdFilter(project)?.let {
        GeneratedPbGoIdFilter(it, protoFileBaseName)
    }
}

class GeneratedPbGoIdFilter(private val it: Map<Int, String>,
                            private val protoFileBaseName: String) : IdFilter() {
    override fun containsFileId(id: Int): Boolean {
        return it.get(id) == "$protoFileBaseName.pb.go"
    }
}

class PbGoIdFilter(private val idToFnMap: Map<Int, String>) : IdFilter() {
    override fun containsFileId(id: Int): Boolean {
        return idToFnMap.containsKey(id)
    }
}
