package com.github.idell.gitlabconnect.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "org.intellij.sdk.settings.GitlabConnectSettings",
    storages = [Storage("gitlabConnectGlobalSettings.xml")]
)
class GitlabConnectPluginSettings private constructor() : PersistentStateComponent<HostSettings> {

    private var state: HostSettings = HostSettings()

    override fun getState(): HostSettings {
        synchronized(this) {
            return state
        }
    }

    override fun loadState(state: HostSettings) {
        synchronized(this) {
            this.state = state
        }
    }

    companion object {
        @JvmStatic
        fun hostSettings(): HostSettings = ServiceManager.getService(GitlabConnectPluginSettings::class.java).getState()
    }
}
