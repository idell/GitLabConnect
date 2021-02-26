package com.github.idell.gitlabconnect.services

import com.github.idell.gitlabconnect.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
