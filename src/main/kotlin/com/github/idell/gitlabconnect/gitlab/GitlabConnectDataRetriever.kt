package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User
import java.util.Optional
import kotlin.NoSuchElementException

class GitlabConnectDataRetriever(private val gitlabConnectApi: ConnectApi) : ConnectDataRetriever {

    override fun search(projectSearch: ProjectSearch): Optional<ProjectInfo> {
        val projects: List<Project> = gitlabConnectApi.search(projectSearch.pathWithNamespace())
        return try {
            Optional.of(projects.first { project -> project.pathWithNamespace.equals(projectSearch.fullPath()) }.toProjectInfo())
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    override fun getIssues(project: ProjectInfo): Issues {
        return gitlabConnectApi.getIssues(project).toProjectInfo()
    }

    override fun getCurrentUser(): UserInfo {
        return gitlabConnectApi.currentUser().toUserInfo()
    }
}

private fun List<org.gitlab4j.api.models.Issue>.toProjectInfo(): Issues {
    return map { issue ->
        Issue(issue.title, issue.webUrl, issue.labels)
    }.toList()
}

private fun Project.toProjectInfo(): ProjectInfo {
    return ProjectInfo(this.id, this.path, this.namespace.path)
}
private fun User.toUserInfo(): UserInfo {
    return UserInfo(this.id, this.name, this.state)
}
