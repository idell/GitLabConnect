package com.github.idell.gitlabconnect.ui.settings

import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBEmptyBorder

class ConnectionResultFactory {
    private var connectionResult = JBTextField(CONNECTION_RESULT_SIZE)

    fun createConnectionResult(): JBTextField {
        connectionResult.isEditable = false
        connectionResult.isOpaque = false
        connectionResult.border = JBEmptyBorder(0)
        return connectionResult
    }

    companion object {
        private const val CONNECTION_RESULT_SIZE = 20
    }
}
