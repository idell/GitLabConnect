package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.gitlab.GitlabConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.storage.PasswordStorage
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBEmptyBorder
import java.awt.Color
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent(connectionHost: String, privateToken: String) {
    private var mainPanel: JPanel
    private val hostName = JBTextField(connectionHost)
    private val connectionToken = JPasswordField(privateToken)
    private val token2 = JBTextField(privateToken)
    private var connectionResult: JBTextField = ConnectionResultFactory().createConnectionResult()

    init {
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, HOST)), hostName, 1, false)
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, TOKEN)), token2, 1)
            .addLabeledComponent(JBLabel(""), testConnectionPanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getHostNameText(): String = hostName.text

    fun getMyTokenText(): String = token2.text

    private fun testConnectionPanel(): JPanel {
        val jPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        val jButton = JButton(GitlabConnectBundle.message(BUTTON_TEXT))

        jButton.addActionListener {
            val gitlabConnectDataRetriever = GitlabConnectDataRetriever(GitlabConnectApi(GitlabConfiguration(hostName.text, token2.text)))

            try {
                val currentUser = gitlabConnectDataRetriever.getCurrentUser()
                if (currentUser != null) {
                    success()
                    PasswordStorage(hostName.text).storeToken(token2.text)
                } else {
                    fail()
                }
            } catch (e: Exception) {
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
