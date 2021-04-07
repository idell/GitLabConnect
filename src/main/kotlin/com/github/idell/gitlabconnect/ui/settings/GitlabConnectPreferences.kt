package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {
    private val gitlabTestService = GitlabTestService
    private var settings = GitlabConnectGlobalSettings.get()
    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        settings.tokenConfig.host,
        settings.tokenConfig.token
    )

    override fun createComponent(): JPanel {
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig.host ||
            component.getToken() != settings.tokenConfig.token
    }

    override fun apply() {
        settings = settings.copy(
            enabled = gitlabTestService.test(component.getHost(), component.getToken()),
            tokenConfig = TokenConfiguration(component.getHost(), component.getToken())
        )
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
