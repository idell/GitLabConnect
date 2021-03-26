package com.github.idell.gitlabconnect.services

import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.models.Project
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class GitlabTestIT {

    @Test
    internal fun `given a full path project, returns a valid id`() {
        val apiClient = GitlabConnectDataRetriever(GitlabConnectApi(GitlabConfiguration("https://gitlab.lastminute.com",
                                                                                        "k761oxXGdsyzHR_49DKJ")))
        val first = apiClient.getId("obi1/order-manager")

        assertEquals(2455, first)
    }

    @Test
    internal fun `given a wrong full path project, returns an empty `(){

//        val apiClient = GitlabConnectDataRetriever(GitlabConnectApi(GitlabConfiguration("https://gitlab.lastminute.com",
//                                                                                        "k761oxXGdsyzHR_49DKJ")))

    }

}

class GitlabConnectDataRetriever(private val gitlabConnectApi: GitlabConnectApi) {

    fun getId(pathWitNamespace: String): Int? {
        val projects: List<Project> = gitlabConnectApi.gitLabApi.projectApi.getProjects(pathWitNamespace)
        return projects.first { project -> project.pathWithNamespace.equals(pathWitNamespace) }.id
    }
}

class GitlabConnectApi(gitlabConfiguration: GitlabConfiguration) {
    val gitLabApi = GitLabApi(gitlabConfiguration.host,gitlabConfiguration.token)
}

data class GitlabConfiguration(val host: String, val token: String)
