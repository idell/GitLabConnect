package com.github.idell.gitlabconnect.gitlab.config

import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConfiguration : Configurable {
    private lateinit var appSettingsComponent: GitlabConnectSettingsComponent

    override fun createComponent(): JPanel {
        appSettingsComponent = GitlabConnectSettingsComponent()
        return appSettingsComponent.getPanel()
    }

    override fun isModified(): Boolean {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        var modified: Boolean = !appSettingsComponent.getMyUserNameText().equals(settings.connectionHost)
        modified = modified or (appSettingsComponent.getMyTokenText().equals(settings.privateToken))
        return modified
    }

    override fun apply() {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        settings.connectionHost = appSettingsComponent.getMyUserNameText().text
        settings.privateToken = appSettingsComponent.getMyTokenText().text
    }

    override fun getDisplayName(): String = "Gitlab Connect"
}
