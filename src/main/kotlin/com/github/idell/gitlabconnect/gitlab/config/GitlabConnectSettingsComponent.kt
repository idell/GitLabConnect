package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JBTextField()

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

    fun getMyUserNameText() = hostName

    fun getMyTokenText() = connectionToken
}
