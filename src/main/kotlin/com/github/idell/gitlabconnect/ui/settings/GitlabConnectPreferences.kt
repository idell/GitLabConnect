package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {

    private var settings = GitlabConnectGlobalSettings.get()
    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        settings.tokenConfig.host,
        settings.tokenConfig.token
    )

    override fun createComponent(): JPanel = component.getPanel()

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig.host ||
            component.getToken() != settings.tokenConfig.token
    }

    override fun apply() {
        settings.enabled = true
        settings.tokenConfig.host = component.getHost()
        settings.tokenConfig.token = component.getToken()
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
