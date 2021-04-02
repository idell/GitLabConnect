package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class GitlabConnectSettingsComponent(host: String, token: String) {
    private var mainPanel: JPanel
    private val hostName = JBTextField(host)
    private val connectionToken = JBTextField(token)

    init {
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Enter Gitlab host: "), hostName, 1, false)
            .addLabeledComponent(JBLabel("Enter gitlab connection token:"), connectionToken, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.text
}
