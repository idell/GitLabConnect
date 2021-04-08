package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {

    private var settings = GitlabConnectGlobalSettings.getInstance().state
    private val token = SecureTokenStorage().getToken(settings.tokenConfig.host).orElse("")
    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        settings.tokenConfig.host,
        token
    )

    override fun createComponent(): JPanel {
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig.host ||
            component.getToken() != token
    }

    override fun apply() {
        settings.enabled = GitlabTestService().test(component.getHost(), component.getToken())
        settings.tokenConfig.host = component.getHost()
        SecureTokenStorage().storeToken(component.getHost(), component.getToken())
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
