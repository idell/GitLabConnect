package com.github.idell.gitlabconnect.ui.settings

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GitlabConnectionStatusTest {
    @Test
    internal fun `will connect to gitlab and return connection status`() {
        val connectionStatus = GitlabConnectionStatus().connect()
        assertThat(connectionStatus).isEqualTo(SuccesfulConnectionStatus("aValidToken"))
    }
}


class GitlabConnectionStatus {
    fun connect(): ConnectionStatus {
        return SuccesfulConnectionStatus("aValidToken")
    }

}

sealed class ConnectionStatus

data class SuccesfulConnectionStatus(val token: String) : ConnectionStatus() {

}

