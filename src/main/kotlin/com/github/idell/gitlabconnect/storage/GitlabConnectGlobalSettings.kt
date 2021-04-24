package com.github.idell.gitlabconnect.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(
    name = "org.intellij.sdk.settings.GitlabConnectSettings",
    storages = [Storage("gitlabConnectGlobalSettings.xml")]
      )
class GitlabConnectGlobalSettings private constructor() : PersistentStateComponent<GlobalSettings> {

    private var state: GlobalSettings = GlobalSettings()

    override fun getState(): GlobalSettings {
        synchronized(this) {
            return state
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
