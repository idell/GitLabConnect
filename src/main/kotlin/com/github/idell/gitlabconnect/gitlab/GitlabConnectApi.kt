package com.github.idell.gitlabconnect.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.GitLabApiException
import org.gitlab4j.api.models.Issue
import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User

class GitlabConnectApi(gitlabTokenConfiguration: GitlabTokenConfiguration) : ConnectApi {
    private val gitLabApi = GitLabApi(gitlabTokenConfiguration.host, gitlabTokenConfiguration.token)

    override fun search(projectWithNamespace: GitlabProject): List<Project> {
        return gitLabApi.projectApi.getProjects(projectWithNamespace)
    }

    override fun getIssues(project: ProjectInfo): List<Issue> {
        return gitLabApi.issuesApi.getIssues(project.id).all()
    }

    override fun currentUser(): User {
        try {
            return gitLabApi.userApi.currentUser
        } catch (e: GitLabApiException) {
            throw GitlabConnectException(e)
        }
    }
}

typealias GitlabProject = String
