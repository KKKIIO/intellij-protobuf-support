package com.github.kkkiio.intellij.protobuf.services

import com.github.kkkiio.intellij.protobuf.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
