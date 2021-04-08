package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.GlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {

    private var settings = GitlabConnectGlobalSettings.getInstance().state
    private val gitlabTestService = GitlabTestService
    private val secureTokenStorage: SecureTokenStorage = SecureTokenStorage()
    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        settings.getHost(),
        settings.getToken()
    )

    override fun createComponent(): JPanel {
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig.host ||
            component.getToken() != settings.tokenConfig.token
    }

    override fun apply() {
        secureTokenStorage.storeToken(component.getHost(),component.getToken())
        settings = settings.copy(
            enabled = gitlabTestService.test(component.getHost(), component.getToken()),
            tokenConfig = TokenConfiguration(component.getHost(), component.getToken())
        )
    }

    private fun GlobalSettings.getHost(): String = this.tokenConfig.host
    private fun GlobalSettings.getToken(): String = this.tokenConfig.token

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
