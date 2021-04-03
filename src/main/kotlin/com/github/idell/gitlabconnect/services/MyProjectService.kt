package com.github.idell.gitlabconnect.services

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(GitlabConnectBundle.message("projectService", project.name))
    }
}
