package com.github.idell.gitlabconnect.ui.settings

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GitlabConnectionStatusTest {
    @Test
    internal fun `will connect to gitlab and return connection status`() {
        val connectionStatus = GitlabConnectionStatus().connect()
        assertThat(connectionStatus).isEqualTo(SuccessfulConnectionStatus("aValidToken"))
    }
}


class GitlabConnectionStatus {
    fun connect(): ConnectionStatus {
        return SuccessfulConnectionStatus("aValidToken")
    }

}

sealed class ConnectionStatus

data class SuccessfulConnectionStatus(val token: String) : ConnectionStatus() {

}

