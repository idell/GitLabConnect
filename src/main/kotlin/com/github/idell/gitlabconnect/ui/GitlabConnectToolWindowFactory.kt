package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.ui.issue.listener.ShowIssueListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class GitlabConnectToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val gitlabConnectToolWindow = GitlabConnectToolWindow(
            toolWindow,
            ShowIssueListener(),
            project,
        )

        val content = ContentFactory.SERVICE.getInstance().createContent(
            gitlabConnectToolWindow.geContent(),
            GitlabConnectBundle.message(ISSUES_TAB_NAME),
            false
        )

        toolWindow.contentManager.addContent(content)
    }

    companion object {
        private const val ISSUES_TAB_NAME = "ui.tab.issue.name"
    }
}
