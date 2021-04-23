package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.WithRestMarkDownGitlabConnectApi
import com.github.idell.gitlabconnect.services.restclient.RestClientService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.ui.markdown.GitlabRestMarkDownProcessor
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service
class GitlabConnectApiService(val project: Project) {

    private val gitlabConnectDataRetriever: GitlabConnectApi = GitlabConnectApi.from(
        GitlabConnectGlobalSettings.getInstance().state.tokenConfig.host,
        SecureTokenStorage().getToken(GitlabConnectGlobalSettings.getInstance().state.tokenConfig.host)
            .orElse(""))

    fun isPluginActive(): Boolean {
        return GitlabConnectGlobalSettings.getInstance().state.enabled
    }

    fun getApi(): ConnectApi {
        return WithRestMarkDownGitlabConnectApi(gitlabConnectDataRetriever, GitlabRestMarkDownProcessor(project.service<RestClientService>().get()))
    }
}
