package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.ConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service
class GitlabConnectService(project: Project) {

    private val gitlabConnectDataRetriever: GitlabConnectDataRetriever = GitlabConnectDataRetriever(
        GitlabConnectApi(
            GitlabTokenConfiguration(
                GitlabConnectGlobalSettings.getInstance().state.tokenConfig.host,
                SecureTokenStorage().getToken(GitlabConnectGlobalSettings.getInstance().state.tokenConfig.host).orElse("")
            )
        )
    )

    fun isPluginActive(): Boolean {
        return GitlabConnectGlobalSettings.getInstance().state.enabled
    }

    fun getRetriever(): ConnectDataRetriever {
        return gitlabConnectDataRetriever
    }
}
