package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.GitlabConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.storage.GitlabConnectSettingState
import com.github.idell.gitlabconnect.storage.PasswordStorage
import com.intellij.openapi.wm.ToolWindow
import java.awt.FlowLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class GitlabConnectToolWindow(val toolWindow: ToolWindow) {

    private var myComponent: JPanel? = JPanel()
    private var myComponent2: JLabel? = JLabel()

    init {
        this.render()
    }

    private fun render() {
        myComponent?.layout = FlowLayout()
        val host = GitlabConnectSettingState.getInstance().host
        val gitlabConnectApi = GitlabConnectApi(GitlabConfiguration(host, PasswordStorage(host).getToken()))
        val gitlabConnectDataRetriever = GitlabConnectDataRetriever(gitlabConnectApi)
        myComponent2?.text = gitlabConnectDataRetriever.getCurrentUser().toString()
        myComponent?.add(myComponent2)
    }

    fun geContent(): JComponent? {
        return myComponent
    }
}
