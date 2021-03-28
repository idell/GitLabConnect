package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
        name = "org.intellij.sdk.settings.GitlabConnectSettingState",
        storages = [Storage("GitlabConnectSettingsPlugin.xml")]
      )
class GitlabConnectSettingState : PersistentStateComponent<GitlabConnectSettingState> {
    var userId:String = "aUserId"
    var privateToken:String = "aToken"

    override fun getState(): GitlabConnectSettingState = this

    override fun loadState(state: GitlabConnectSettingState) {
        XmlSerializerUtil.copyBean(state, this);
    }
    companion object {
        @JvmStatic
        fun getInstance(): GitlabConnectSettingState = ServiceManager.getService(GitlabConnectSettingState::class.java)
    }
}