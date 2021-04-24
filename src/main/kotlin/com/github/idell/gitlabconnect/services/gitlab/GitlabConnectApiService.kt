package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.WithRestMarkDownGitlabConnectApi
import com.github.idell.gitlabconnect.services.restclient.RestClientService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.ui.markdown.GitlabRestMarkDownProcessor
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service
class GitlabConnectApiService(val project: Project) {

    fun isPluginActive(): Boolean {
        return GitlabConnectGlobalSettings.getInstance().state.enabled
    }

    fun getApi(): ConnectApi {
        val service = project.service<GitlabConfigService>()

        return WithRestMarkDownGitlabConnectApi(
            GitlabConnectApi.from(service.get().host, service.get().token),
            GitlabRestMarkDownProcessor(project.service<RestClientService>().get())
                                               )
    }
}
