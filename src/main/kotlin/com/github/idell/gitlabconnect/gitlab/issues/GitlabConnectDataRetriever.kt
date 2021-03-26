package com.github.idell.gitlabconnect.gitlab.issues

import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.ConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.Issues
import org.gitlab4j.api.models.Project
import java.util.Optional
import kotlin.NoSuchElementException

class GitlabConnectDataRetriever(private val gitlabConnectApi: ConnectApi) : ConnectDataRetriever {

    override fun getId(pathWitNamespace: String): Optional<Int> {
        val projects: List<Project> = gitlabConnectApi.getGitlabApi().projectApi.getProjects(pathWitNamespace)
        return try {
            Optional.of(projects.first { project -> project.pathWithNamespace.equals(pathWitNamespace) }.id)
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    override fun getIssues(projectId: String): Issues {
        return gitlabConnectApi.getGitlabApi().issuesApi.getIssues(projectId).toIssues()
    }
}

private fun List<org.gitlab4j.api.models.Issue>.toIssues(): Issues {
    return map { issue ->
        Issue(issue.title, issue.webUrl, issue.labels)
    }.toList()
}
