package com.github.idell.gitlabconnect.services

import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.models.Project
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class GitlabTestIT {

    @Test
    internal fun `given a full path project, returns a valid id`() {
        val gitlabConfiguration = GitlabConfiguration("https://gitlab.lastminute.com", "k761oxXGdsyzHR_49DKJ")
        val apiClient = ApiClient(gitlabConfiguration)
        val first = apiClient.getId("obi1/order-manager")

        assertEquals(2455, first)
    }

}

class ApiClient(private val gitlabConfiguration: GitlabConfiguration) {

    private val gitLabApi: GitLabApi = GitLabApi(GitLabApi.ApiVersion.V4,
                                                 this.gitlabConfiguration.host,
                                                 this.gitlabConfiguration.token)

    fun getId(pathWitNamespace: String): Int? {

        val projects: List<Project> = gitLabApi.projectApi.getProjects(pathWitNamespace)
        return projects.first { project -> project.pathWithNamespace.equals(pathWitNamespace) }.id
    }
}

data class GitlabConfiguration(val host: String, val token: String)
