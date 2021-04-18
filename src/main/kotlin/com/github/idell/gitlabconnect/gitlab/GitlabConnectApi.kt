package com.github.idell.gitlabconnect.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.GitLabApiException
import org.gitlab4j.api.models.Issue as GitlabIssue
import org.gitlab4j.api.models.Project as GitlabApiProject
import org.gitlab4j.api.models.User as GitlabApiUser

class GitlabConnectApi(gitlabTokenConfiguration: GitlabTokenConfiguration) : ConnectApi {
    private val gitLabApi = GitLabApi(gitlabTokenConfiguration.host, gitlabTokenConfiguration.token)

    override fun search(projectWithNamespace: GitlabProject): List<GitlabApiProject> {
        return gitLabApi.projectApi.getProjects(projectWithNamespace)
    }

    override fun getIssues(project: ProjectInfo): List<GitlabIssue> {
        return gitLabApi.issuesApi.getIssues(project.id as Any)
    }
    @Deprecated("Use the one implemented by WithRestMarkDownGitlabConnectApi until issue #30 is not solved")
    override fun markdownApi(issue: Issue): String {
        try {
            return gitLabApi.markdownApi.getMarkdown(issue.description).html
        } catch (gitlabApiException: GitLabApiException) {
            throw GitlabProcessException(gitlabApiException)
        }
    }

    override fun currentUser(): GitlabApiUser {
        try {
            return gitLabApi.userApi.currentUser
        } catch (e: GitLabApiException) {
            throw GitlabConnectException(e)
        }
    }
}

typealias GitlabProject = String
