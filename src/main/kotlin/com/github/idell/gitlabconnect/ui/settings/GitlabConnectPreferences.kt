package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.GlobalSettings
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {
    private lateinit var component: GitlabPreferencesComponent
    private var settings = GlobalSettings()

    override fun createComponent(): JPanel {
        settings = GitlabConnectGlobalSettings.getInstance().state
        component = GitlabPreferencesComponent(
            settings.tokenConfig?.host!!,
            settings.tokenConfig?.token!!
        )
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig?.host ||
            component.getToken() != settings.tokenConfig?.token
    }

    override fun apply() {
        settings.tokenConfig?.host = component.getHost()
        settings.tokenConfig?.token = component.getToken()
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
