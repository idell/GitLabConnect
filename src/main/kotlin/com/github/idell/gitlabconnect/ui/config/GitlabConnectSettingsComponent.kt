package com.github.idell.gitlabconnect.ui.config

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBEmptyBorder
import java.awt.Color
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JPasswordField()
    private var connectionResult = ConnectionResultFactory().createConnectionResult()

    init {
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, HOST)), hostName, 1, false)
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, TOKEN)), connectionToken, 1)
            .addLabeledComponent(JBLabel(""), testConnectionPanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.password.toString()

    private fun testConnectionPanel(): JPanel {
        val jPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        val jButton = JButton(GitlabConnectBundle.message(BUTTON_TEXT))
        var switch = false

        jButton.addActionListener {
            switch = if (!switch) {
                success()
            } else {
                fail()
            }
        }
        configureConnectionResult()
        jPanel.add(jButton)
        jPanel.add(connectionResult)
        return jPanel
    }

    private fun configureConnectionResult() {
        connectionResult.isEditable = false
        connectionResult.isOpaque = false
        connectionResult.border = JBEmptyBorder(0)
    }

    private fun success(): Boolean {
        connectionResult.text = GitlabConnectBundle.message(CONNECTION_RESULT, CONNECTION_SUCCESS)
        connectionResult.foreground = DARK_GREEN
        return true
    }

    private fun fail(): Boolean {
        connectionResult.text = GitlabConnectBundle.message(CONNECTION_RESULT, CONNECTION_FAILED)
        connectionResult.foreground = Color.RED
        return false
    }

    companion object {
        private const val BUTTON_TEXT = "ui.settings.connection.test.button"
        private const val CONNECTION_RESULT = "ui.settings.connection.test.result"
        const val CONNECTION_LABEL = "ui.settings.connection.label"
        const val TOKEN = "token"
        const val HOST = "host"
        private const val CONNECTION_SUCCESS = "success"
        private const val CONNECTION_FAILED = "failed"

        private val DARK_GREEN = Color(3, 146, 94)
    }
}
