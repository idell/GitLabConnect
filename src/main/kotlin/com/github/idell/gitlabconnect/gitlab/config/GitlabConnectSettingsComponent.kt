package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBEmptyBorder
import java.awt.Color
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JPasswordField()
    private var connectionResult = JBTextField(20)

    init {
        connectionResult.isEditable = false
        connectionResult.isOpaque = false
        connectionResult.size = Dimension(5, 5)
        connectionResult.border = JBEmptyBorder(0)

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Gitlab host: "), hostName, 1, false)
            .addLabeledComponent(JBLabel("Gitlab connection token:"), connectionToken, 1)
            .addLabeledComponent(JBLabel(""), testConnectionPanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun testConnectionPanel(): JPanel {
        val jPanel = JPanel()
        jPanel.size = Dimension(50, 10)
        jPanel.border = JBEmptyBorder(1)
        jPanel.isOpaque = false

        val jButton = JButton(BUTTON_TEXT)
        var switch = false

        jButton.addActionListener {
            if (!switch) {
                success()
                switch = true
            } else {
                connectionResult.text = CONNECTION_FAILED
                connectionResult.foreground = Color.RED
                switch = false
            }
        }
        jPanel.add(jButton)
        jPanel.add(connectionResult)

        return jPanel
    }

    private fun success() {
        connectionResult.text = CONNECTION_SUCESS
        connectionResult.foreground = DARK_GREEN
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.password.toString()

    companion object {
        private const val BUTTON_TEXT = "Test connection"
        private const val CONNECTION_SUCESS = "Connection success"
        private const val CONNECTION_FAILED = "Connection failed"
        private val DARK_GREEN = Color(3, 146, 94)
    }
}
