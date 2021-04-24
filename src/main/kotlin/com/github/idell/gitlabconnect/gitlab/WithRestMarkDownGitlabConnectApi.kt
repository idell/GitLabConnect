package com.github.idell.gitlabconnect.gitlab

import com.github.idell.gitlabconnect.ui.markdown.MarkDownProcessor
import java.util.Optional

class WithRestMarkDownGitlabConnectApi(
    private val gitlabConnectApi: ConnectApi,
    private val markDownProcessor: MarkDownProcessor
) : ConnectApi {
    override fun search(projectSearch: ProjectSearch): Optional<ProjectInfo> = gitlabConnectApi.search(projectSearch)

    override fun getIssues(project: ProjectInfo): Issues = gitlabConnectApi.getIssues(project)

    override fun getCurrentUser(): UserInfo = gitlabConnectApi.getCurrentUser()

    override fun markdownApi(issue: Issue): String = markDownProcessor.process(issue)
}
