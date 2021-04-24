package com.github.idell.gitlabconnect.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.GitLabApiException
import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User
import java.util.Optional

class GitlabConnectApi(private val gitLabApi: GitLabApi) : ConnectApi {

    constructor(gitlabTokenConfiguration: GitlabTokenConfiguration) :
        this(GitLabApi(gitlabTokenConfiguration.host, gitlabTokenConfiguration.token))

    override fun search(projectSearch: ProjectSearch): Optional<ProjectInfo> {
        val projects: List<Project> = gitLabApi.projectApi.getProjects(projectSearch.pathWithNamespace())

        return try {
            Optional.of(projects.first { project -> project.pathWithNamespace.equals(projectSearch.fullPath()) }.to())
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    override fun getIssues(project: ProjectInfo): Issues {
        return gitLabApi.issuesApi.getIssues(project.id as Any).to()
    }

    override fun getCurrentUser(): UserInfo {
        return try {
            gitLabApi.userApi.currentUser.to()
        } catch (e: GitLabApiException) {
            throw GitlabConnectException(e)
        }
    }

    override fun markdownApi(issue: Issue): String {
        return try {
            gitLabApi.markdownApi.getMarkdown(issue.description).html
        } catch (gitlabApiException: GitLabApiException) {
            throw GitlabProcessException(gitlabApiException)
        }
    }

    companion object {
        fun from(host: String, token: String): GitlabConnectApi =
            GitlabConnectApi(GitlabTokenConfiguration(host, token))
    }
}

private fun List<org.gitlab4j.api.models.Issue>.to(): Issues {
    return map { issue ->
        Issue(issue.title, issue.webUrl, issue.labels, issue.description)
    }.toList()
}

private fun Project.to(): ProjectInfo {
    return ProjectInfo(this.id, this.path, this.namespace.path)
}
private fun User.to(): UserInfo {
    return UserInfo(this.id, this.name, this.state)
}
