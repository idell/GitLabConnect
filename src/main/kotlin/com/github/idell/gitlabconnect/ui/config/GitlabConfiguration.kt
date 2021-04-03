package com.github.idell.gitlabconnect.ui.config

import com.github.idell.gitlabconnect.GitlabConnectBundle
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
        var modified: Boolean = appSettingsComponent.getMyUserNameText() != settings.connectionHost
        modified = modified or (appSettingsComponent.getMyTokenText() == settings.privateToken)
        return modified
    }

    override fun apply() {
        val settings: GitlabConnectSettingState = GitlabConnectSettingState.getInstance()
        settings.connectionHost = appSettingsComponent.getMyUserNameText()
        settings.privateToken = appSettingsComponent.getMyTokenText()
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
