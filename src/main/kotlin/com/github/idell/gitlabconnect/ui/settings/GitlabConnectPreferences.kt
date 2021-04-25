package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.services.configuration.GitlabConnectConfigurationApplicationService.Companion.gitlabConnectConfigurationApplicationService
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

class GitlabConnectPreferences : Configurable {

    private var component: GitlabPreferencesComponent = GitlabPreferencesComponent(
        gitlabConnectConfigurationApplicationService().gitlabTokenConfiguration().host,
        gitlabConnectConfigurationApplicationService().gitlabTokenConfiguration().token
    )

    override fun createComponent(): JPanel {
        return component.getPanel()
    }

    override fun isModified(): Boolean {
        return GitlabTokenConfiguration(component.getHost(), component.getToken()) !=
            gitlabConnectConfigurationApplicationService().gitlabTokenConfiguration()
    }

    override fun apply() {
        gitlabConnectConfigurationApplicationService().save(
            GitlabTokenConfiguration(component.getHost(), component.getToken())
        )
    }

    override fun getDisplayName(): String = GitlabConnectBundle.getMessage("ui.plugin.name")
}
