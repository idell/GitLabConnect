package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.storage.GitlabConnectSettingState
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConfiguration : Configurable {
    private lateinit var appSettingsComponent: GitlabConnectSettingsComponent

    override fun createComponent(): JPanel {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        appSettingsComponent = GitlabConnectSettingsComponent(settings.host, settings.token)
        return appSettingsComponent.getPanel()
    }

    override fun isModified(): Boolean {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        var modified: Boolean = appSettingsComponent.getMyUserNameText() != settings.host
        modified = modified or (appSettingsComponent.getMyTokenText() == settings.token)
        return modified
    }

    override fun apply() {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        settings.host = appSettingsComponent.getMyUserNameText()
        settings.token = appSettingsComponent.getMyTokenText()
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
