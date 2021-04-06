package com.github.idell.gitlabconnect.storage

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.gitlab.GitlabConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.intellij.ui.components.JBTextField
import org.gitlab4j.api.models.HealthCheckStatus
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class StoreTokenActionListener(private val host: String, private val token: String, private val connectionResult: JBTextField) :
        ActionListener {

    override fun actionPerformed(e: ActionEvent?) {

//        try {
//            val currentUser = gitlabConnectDataRetriever.getCurrentUser()
//            if (currentUser!=null) {
//                success()
//                PasswordStorage(host).storeToken(token)
//            } else {
//                fail()
//            }
//        } catch (e: Exception) {
//            fail()
//        }

    }

    private fun success(): Boolean {
        connectionResult.text = GitlabConnectBundle.message(CONNECTION_RESULT,
                                                            CONNECTION_SUCCESS)
        connectionResult.foreground = DARK_GREEN
        return true
    }

    private fun fail(): Boolean {
        connectionResult.text = GitlabConnectBundle.message(CONNECTION_RESULT,
                                                            CONNECTION_FAILED)
        connectionResult.foreground = Color.RED
        return false
    }

    companion object {
        private const val CONNECTION_RESULT = "ui.settings.connection.test.result"
        private const val CONNECTION_SUCCESS = "success"
        private const val CONNECTION_FAILED = "failed"
        private val DARK_GREEN = Color(3, 146, 94)
    }

}