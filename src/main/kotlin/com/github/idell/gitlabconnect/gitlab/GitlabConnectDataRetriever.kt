package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User
import java.util.Optional

class GitlabConnectDataRetriever(private val gitlabConnectApi: ConnectApi) : ConnectDataRetriever {

    override fun search(projectSearch: ProjectSearch): Optional<ProjectInfo> {
        val projects: List<Project> = gitlabConnectApi.search(projectSearch.pathWithNamespace())
        return try {
            Optional.of(projects.first { project -> project.pathWithNamespace.equals(projectSearch.fullPath()) }.to())
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    override fun getIssues(project: ProjectInfo): Issues {
        return gitlabConnectApi.getIssues(project).to()
    }

    override fun getCurrentUser(): UserInfo {
        return gitlabConnectApi.currentUser().to()
    }

    companion object {
        fun from(host: String, token: String): GitlabConnectDataRetriever =
            GitlabConnectDataRetriever(GitlabConnectApi(GitlabTokenConfiguration(host, token)))
    }
}

private fun List<org.gitlab4j.api.models.Issue>.to(): Issues {
    return map { issue ->
        Issue(issue.title, issue.webUrl, issue.labels)
    }.toList()
}

private fun Project.to(): ProjectInfo {
    return ProjectInfo(this.id, this.path, this.namespace.path)
}
private fun User.to(): UserInfo {
    return UserInfo(this.id, this.name, this.state)
}
