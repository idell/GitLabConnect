package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.Color
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JPasswordField

class GitlabConnectSettingsComponent {
    private var mainPanel: JPanel
    private val hostName = JBTextField()
    private val connectionToken = JPasswordField()
    private var connectionResult = JBTextField()


    init {
        connectionResult.isEditable = false
        connectionResult.isOpaque = false

        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("Gitlab host: "), hostName, 1, false)
                .addLabeledComponent(JBLabel("Gitlab connection token:"), connectionToken, 1)
                .addComponentToRightColumn(testMeButton())
                .addLabeledComponent(JBLabel(),connectionResult)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    private fun testMeButton(): JButton {
        val jButton = JButton("Test connection")
        var switch = false

        jButton.addActionListener {
            if (!switch) {
                success()
                switch = true
            } else {
                connectionResult.text = "Connection failed"
                connectionResult.foreground = Color.RED
                switch = false
            }
        }
        return jButton
    }

    private fun success() {
        connectionResult.text = "Connection success"
        connectionResult.foreground = Color.GREEN
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getMyUserNameText(): String = hostName.text

    fun getMyTokenText(): String = connectionToken.password.toString()

}
