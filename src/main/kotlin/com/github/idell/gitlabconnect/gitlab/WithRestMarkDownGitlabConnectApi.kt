package com.github.idell.gitlabconnect.gitlab

import com.github.idell.gitlabconnect.ui.markdown.MarkDownProcessor
import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User

class WithRestMarkDownGitlabConnectApi(
    private val gitlabConnectApi: GitlabConnectApi,
    private val markDownProcessor: MarkDownProcessor
) : ConnectApi {
    override fun search(projectWithNamespace: GitlabProject): List<Project> =
        gitlabConnectApi.search(projectWithNamespace)

    override fun getIssues(project: ProjectInfo): List<org.gitlab4j.api.models.Issue> =
        gitlabConnectApi.getIssues(project)

    override fun currentUser(): User = gitlabConnectApi.currentUser()

    override fun markdownApi(issue: Issue, project: ProjectInfo): String = markDownProcessor.process(issue, project)
}
