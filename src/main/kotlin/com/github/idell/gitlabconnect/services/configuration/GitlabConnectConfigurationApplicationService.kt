package com.github.idell.gitlabconnect.services.configuration

import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestConnectionService.Companion.gitlabTestConnectionService
import com.github.idell.gitlabconnect.storage.GitlabConnectPluginSettings.Companion.hostSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.storage.TokenData
import com.intellij.openapi.components.ServiceManager

class GitlabConnectConfigurationApplicationService {

    fun gitlabTokenConfiguration(): GitlabTokenConfiguration {
        return hostSettings()
            .host
            ?.let { GitlabTokenConfiguration(it, SecureTokenStorage().getToken(it).orElse("")) }
            ?: GitlabTokenConfiguration("", "")
    }

    fun isEnabled(): Boolean {
        return hostSettings().enabled
    }

    fun save(gitlabTokenConfiguration: GitlabTokenConfiguration) {
        gitlabTestConnectionService()
            .test(gitlabTokenConfiguration)
            .let { save(gitlabTokenConfiguration, it) }
    }

    private fun save(gitlabTokenConfiguration: GitlabTokenConfiguration, enabled: Boolean) {
        hostSettings().enabled = enabled
        SecureTokenStorage().storeToken(TokenData(gitlabTokenConfiguration.host, gitlabTokenConfiguration.token))
        hostSettings().host = gitlabTokenConfiguration.host
    }

    companion object {
        @JvmStatic
        fun gitlabConnectConfigurationApplicationService(): GitlabConnectConfigurationApplicationService =
            ServiceManager.getService(GitlabConnectConfigurationApplicationService::class.java)
    }
}
