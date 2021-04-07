package com.github.idell.gitlabconnect.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "org.intellij.sdk.settings.GitlabConnectSettings",
    storages = [Storage("gitlabConnectGlobalSettings.xml")]
)
class GitlabConnectGlobalSettings : PersistentStateComponent<GlobalSettings> {

    private var state: GlobalSettings = GlobalSettings()
//TODO ivn cosi facendo ogni volta che ricarica mi trovo uno stato nuovo (state: GlobalSettings = GlobalSettings()), invece devo recuperare il vecchio
    override fun getState(): GlobalSettings {
        synchronized(this) {
            state.tokenConfig.host
            val token = SecureTokenStorage().getToken(state.tokenConfig.host)
            return GlobalSettings(tokenConfig = TokenConfiguration(host = state.tokenConfig.host,
                                                                   token = token.orElse("")))
        }
    }

    override fun loadState(state: GlobalSettings) {
        synchronized(this) {
            this.state = state
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(): GitlabConnectGlobalSettings =
            ServiceManager.getService(GitlabConnectGlobalSettings::class.java)

        @JvmStatic
        fun get(): GlobalSettings = ServiceManager.getService(GitlabConnectGlobalSettings::class.java).getState()
    }
}
