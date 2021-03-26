package com.github.idell.gitlabconnect.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class GitlabConnectToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val gitlabConnectToolWindow = GitlabConnectToolWindow(toolWindow)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(gitlabConnectToolWindow.geContent(), "Gitlab", false)
        toolWindow.contentManager.addContent(content)
    }
}
