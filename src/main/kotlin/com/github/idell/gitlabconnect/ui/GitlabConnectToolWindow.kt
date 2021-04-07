package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.GitlabConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.storage.GitlabConnectSettingState
import com.github.idell.gitlabconnect.storage.PasswordStorage
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.components.BorderLayoutPanel
import javax.swing.JComponent
import javax.swing.JSplitPane


class GitlabConnectToolWindow(val toolWindow: ToolWindow) {

    private var leftComponent: JBPanel<BorderLayoutPanel> = JBPanel()
    private var rightComponent : JBTextField = JBTextField("stocazzo")
    private var externalPanel: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftComponent, rightComponent)

    init {
        this.render()
    }

    private fun render() {
        val list: JBList<Issue> = JBList(listOf(Issue("A", "http://www.google.it", listOf("technical debt")),
                                                Issue("B", "http://www.google.it", listOf("technical debt")),
                                                Issue("C", "http://www.google.it", listOf("technical debt"))))
        leftComponent.add(list)
        rightComponent.isEditable = false
        list.addListSelectionListener {

            rightComponent.text=list.selectedValue.link
        }
        val host = GitlabConnectSettingState.getInstance().host
        val gitlabConnectApi = GitlabConnectApi(GitlabConfiguration(host, PasswordStorage(host).getToken()))
        val gitlabConnectDataRetriever = GitlabConnectDataRetriever(gitlabConnectApi)
        externalPanel.dividerSize = 1
    }

    fun geContent(): JComponent {
        return externalPanel
    }
}
