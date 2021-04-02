package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JPasswordField()

    init {
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Gitlab host: "), hostName, 1, false)
            .addLabeledComponent(JBLabel("Gitlab connection token:"), connectionToken, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.text
}
