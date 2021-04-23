package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.gitlab.WithRestMarkDownGitlabConnectApi
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.ui.issue.listener.ShowIssueListener
import com.github.idell.gitlabconnect.ui.markdown.GitlabRestMarkDownProcessor
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class GitlabConnectToolWindowFactory : ToolWindowFactory {
    private val globalSettings = GitlabConnectGlobalSettings.getInstance()
    private val tokenStorage = SecureTokenStorage()

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val gitlabTokenConfiguration = retrieveGitlabTokenConfiguration()
        val gitlabConnectApi = GitlabConnectApi(gitlabTokenConfiguration)
        val gitlabConnectToolWindow = GitlabConnectToolWindow(
            toolWindow,
            ShowIssueListener(),
            WithRestMarkDownGitlabConnectApi(
                gitlabConnectApi,
                GitlabRestMarkDownProcessor(Fuel)
            )
        )
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(
            gitlabConnectToolWindow.geContent(),
            GitlabConnectBundle.message(ISSUES_TAB_NAME),
            false
        )
        toolWindow.contentManager.addContent(content)
    }

    private fun retrieveGitlabTokenConfiguration(): GitlabTokenConfiguration {
        val host = globalSettings.state.tokenConfig.host
        val token = tokenStorage.getToken(host).orElseThrow {
            GitlabProcessException(GitlabConnectBundle.message(TOKEN_NOT_FOUND_MESSAGE, host))
        }
        return GitlabTokenConfiguration(host, token)
    }

    companion object {
        private const val ISSUES_TAB_NAME = "ui.tab.isssue.name"
        private const val TOKEN_NOT_FOUND_MESSAGE = "storage.token.not-found"
    }
}
