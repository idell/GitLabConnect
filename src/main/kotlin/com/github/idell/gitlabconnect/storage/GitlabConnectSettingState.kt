package com.github.idell.gitlabconnect.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "org.intellij.sdk.settings.GitlabConnectSettings",
    storages = [Storage("gitlabConnectSettingsPlugin.xml")]
)
class GitlabConnectSettingState : PersistentStateComponent<GitlabConnectSettingState> {
    var host: String = "<insert your connection host here>"
    var token: String = "<insert your connection token here>"

    override fun getState(): GitlabConnectSettingState = this

    override fun loadState(state: GitlabConnectSettingState) {
        XmlSerializerUtil.copyBean(state, this)
    }
    companion object {
        @JvmStatic
        fun getInstance(): GitlabConnectSettingState = ServiceManager.getService(GitlabConnectSettingState::class.java)
    }
}
