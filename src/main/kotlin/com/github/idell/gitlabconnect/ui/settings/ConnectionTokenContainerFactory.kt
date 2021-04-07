package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JPasswordField

class ConnectionTokenContainerFactory(private val connectionToken: JPasswordField) {
    fun create(): JPanel {
        val connectionTokenContainer = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        connectionTokenContainer.add(connectionToken)
        connectionToken.columns = TOKEN_INPUT_COLUMNS
        val showTokenCheckBox = JCheckBox(GitlabConnectBundle.message(SHOW_TOKEN_LABEL))
        showTokenCheckBox.addActionListener { showTokenListener(it) }
        connectionTokenContainer.add(showTokenCheckBox)
        return connectionTokenContainer
    }

    private fun showTokenListener(it: ActionEvent) {
        val source: JCheckBox = it.source as JCheckBox
        if (source.isSelected) {
            connectionToken.echoChar = CLEAR_TOKEN
        } else {
            connectionToken.echoChar = HIDDEN_TOKEN
        }
    }

    companion object {
        const val SHOW_TOKEN_LABEL = "ui.settings.show.token"
        const val HIDDEN_TOKEN = '\u25CF'
        const val CLEAR_TOKEN = 0.toChar()
        const val TOKEN_INPUT_COLUMNS = 50
    }
}
