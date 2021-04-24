package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.services.gitlab.GitlabConfigService
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.storage.TokenData
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {
    private val secureTokenStorage = SecureTokenStorage()
    private var settings = GitlabConnectGlobalSettings.getInstance().state
    private val token = secureTokenStorage.getToken(settings.tokenConfig.host).orElse("")
    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        settings.tokenConfig.host,
        token
    )

    override fun createComponent(): JPanel {
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return component.getHost() != settings.tokenConfig.host ||
            component.getToken() != secureTokenStorage.getToken(settings.tokenConfig.host).orElse("")
    }

    override fun apply() {
        settings.enabled = GitlabTestService().test(component.getHost(), component.getToken())
        settings.tokenConfig.host = component.getHost()
        secureTokenStorage.storeToken(TokenData(component.getHost(), component.getToken()))
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
