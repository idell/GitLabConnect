package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.storage.GitlabConnectSettingState
import com.github.idell.gitlabconnect.storage.PasswordStorage
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConfigurable : Configurable {
    private lateinit var appSettingsComponent: GitlabConnectSettingsComponent

    override fun createComponent(): JPanel {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        val token = PasswordStorage(settings.host).getToken()
        appSettingsComponent = GitlabConnectSettingsComponent(settings.host, token)
        return appSettingsComponent.getPanel()
    }

    override fun isModified(): Boolean {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        var modified: Boolean = appSettingsComponent.getHostNameText() != settings.host
        modified = modified or (appSettingsComponent.getMyTokenText() == PasswordStorage(settings.host).getToken())
        return modified
    }

    override fun apply() {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        settings.host = appSettingsComponent.getHostNameText()
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
