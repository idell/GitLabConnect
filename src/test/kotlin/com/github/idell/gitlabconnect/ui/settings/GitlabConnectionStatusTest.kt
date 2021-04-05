package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.gitlab.GitlabConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.storage.GitlabConnectSettingState
import org.assertj.core.api.Assertions.assertThat
import org.gitlab4j.api.models.HealthCheckStatus
import org.junit.jupiter.api.Test

class GitlabConnectionStatusTest {
    // TODO ivn this test should be improved
    @Test
    internal fun `will connect to gitlab and return connection status`() {
        val connectionStatus = GitlabConnectionStatus().connect()
        assertThat(connectionStatus).isEqualTo(SuccessfulConnectionStatus("aValidToken"))
    }

    @Test
    internal fun `cant connect to gitlab and return a failed connection status`() {
        val connectionStatus = GitlabConnectionStatus().connect()
        assertThat(connectionStatus).isEqualTo(FailedConnectionStatus)
    }
}

class GitlabConnectionStatus {
    // TODO ivn this should be used when "Test connection" button is pressed
    fun connect(): ConnectionStatus {
        val connectSettingState = GitlabConnectSettingState.getInstance().state
        val gitlabConnectDataRetriever =
            GitlabConnectDataRetriever(
                GitlabConnectApi(
                    GitlabConfiguration(
                        connectSettingState.host,
                        connectSettingState.token
                    )
                )
            )
        val connect = gitlabConnectDataRetriever.connect()
        return if (connect == HealthCheckStatus.OK)
            SuccessfulConnectionStatus("aValidToken")
        else FailedConnectionStatus
    }
}

sealed class ConnectionStatus

data class SuccessfulConnectionStatus(val token: String) : ConnectionStatus()

object FailedConnectionStatus : ConnectionStatus()
