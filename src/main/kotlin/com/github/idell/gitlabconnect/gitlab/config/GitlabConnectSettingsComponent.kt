package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBEmptyBorder
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JPasswordField()
    private var connectionResult = JBTextField(CONNECTION_RESULT_SIZE)

    init {
        connectionResult.isEditable = false
        connectionResult.isOpaque = false
        connectionResult.border = JBEmptyBorder(0)

        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Gitlab host: "), hostName, 1, false)
            .addLabeledComponent(JBLabel("Gitlab connection token:"), connectionToken, 1)
            .addLabeledComponent(JBLabel(""), testConnectionPanel())
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun testConnectionPanel(): JPanel {
        val jPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        val jButton = JButton(BUTTON_TEXT)
        var switch = false

        jButton.addActionListener {
            switch = if (!switch) {
                success()
            } else {
                fail()
            }
        }
        jPanel.add(jButton)
        jPanel.add(connectionResult)
        return jPanel
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.password.toString()

    private fun success(): Boolean {
        connectionResult.text = CONNECTION_SUCESS
        connectionResult.foreground = DARK_GREEN
        return true
    }

    private fun fail(): Boolean {
        connectionResult.text = CONNECTION_FAILED
        connectionResult.foreground = Color.RED
        return false
    }

    companion object {
        private const val BUTTON_TEXT = "Test connection"
        private const val CONNECTION_SUCESS = "Connection success"
        private const val CONNECTION_FAILED = "Connection failed"
        private const val CONNECTION_RESULT_SIZE = 20
        private val DARK_GREEN = Color(3, 146, 94)
    }
}
