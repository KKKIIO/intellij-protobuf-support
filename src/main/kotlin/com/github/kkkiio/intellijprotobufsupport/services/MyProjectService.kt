package com.github.kkkiio.intellijprotobufsupport.services

import com.intellij.openapi.project.Project
import com.github.kkkiio.intellijprotobufsupport.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
