package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.services.gitlab.GitlabConnectApiService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.ui.issue.listener.ShowIssueListener
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class GitlabConnectToolWindowFactory : ToolWindowFactory {
    private val globalSettings = GitlabConnectGlobalSettings.getInstance()
    private val tokenStorage = SecureTokenStorage()

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val gitlabConnectToolWindow = GitlabConnectToolWindow(
            toolWindow,
            ShowIssueListener(),
            project.service<GitlabConnectApiService>().getApi(),
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
