package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabConnectException
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
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent(connectionHost: String, privateToken: String) {
    private var mainPanel: JPanel
    private val hostName = JBTextField(connectionHost)
    private val connectionToken = JPasswordField(privateToken)
    private val connectionTokenContainer = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
    private var connectionResult: JBTextField = ConnectionResultFactory().createConnectionResult()

    init {
        connectionTokenContainer()

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, HOST)),
                                 hostName,
                                 1,
                                 false)
            .addLabeledComponent(JBLabel(GitlabConnectBundle.message(CONNECTION_LABEL, TOKEN)),
                                 connectionTokenContainer,
                                 1)
            .addLabeledComponent(JBLabel(""), testConnectionPanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getHostNameText(): String = hostName.text

    fun getMyTokenText(): String = String(connectionToken.password)

    private fun connectionTokenContainer() {
        connectionTokenContainer.add(connectionToken)
        connectionToken.columns = TOKEN_INPUT_COLUMNS
        val jCheckBox = JCheckBox(GitlabConnectBundle.message(SHOW_TOKEN_LABEL))
        jCheckBox.addActionListener { showTokenListener(it) }
        connectionTokenContainer.add(jCheckBox)
    }

    private fun showTokenListener(it: ActionEvent) {
        val source: JCheckBox = it.source as JCheckBox
        if (source.isSelected) {
            connectionToken.echoChar = CLEAR_TOKEN
        } else {
            connectionToken.echoChar = HIDDEN_TOKEN
        }
    }

    private fun testConnectionPanel(): JPanel {
        val jPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        val jButton = JButton(GitlabConnectBundle.message(BUTTON_TEXT))

        jButton.addActionListener { testConnectionListener() }
        configureConnectionResult()
        jPanel.add(jButton)
        jPanel.add(connectionResult)
        return jPanel
    }

    private fun testConnectionListener() {
        val gitlabConnectDataRetriever =
            GitlabConnectDataRetriever(GitlabConnectApi(GitlabConfiguration(hostName.text, getMyTokenText())))

        try {
            gitlabConnectDataRetriever.getCurrentUser()
            success()
        } catch (e: GitlabConnectException) {
            fail()
        } finally {
            PasswordStorage(hostName.text).storeToken(getMyTokenText())
        }
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
        const val SHOW_TOKEN_LABEL = "ui.settings.show.token"
        const val HIDDEN_TOKEN = '\u25CF'
        const val CLEAR_TOKEN = 0.toChar()
        const val TOKEN_INPUT_COLUMNS = 50
        const val TOKEN = "token"
        const val HOST = "host"
        private const val CONNECTION_SUCCESS = "success"
        private const val CONNECTION_FAILED = "failed"
        private val DARK_GREEN = Color(3, 146, 94)
    }
}
