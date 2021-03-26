package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.models.Project
import java.util.*

class GitlabConnectDataRetriever(private val gitlabConnectApi: ConnectApi) {

    fun getId(pathWitNamespace: String): Optional<Int> {
        val projects: List<Project> = gitlabConnectApi.getGitlabApi().projectApi.getProjects(pathWitNamespace)
        return try {
            Optional.of(projects.first { project -> project.pathWithNamespace.equals(pathWitNamespace) }.id)
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }
}