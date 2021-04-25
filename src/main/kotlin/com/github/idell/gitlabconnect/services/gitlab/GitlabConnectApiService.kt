package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.WithRestMarkDownGitlabConnectApi
import com.github.idell.gitlabconnect.services.restclient.RestClientService
import com.github.idell.gitlabconnect.ui.markdown.GitlabRestMarkDownProcessor
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.github.idell.gitlabconnect.services.configuration.GitlabConnectConfigurationApplicationService.Companion.gitlabConnectConfigurationApplicationService as pluginService

@Service
class GitlabConnectApiService(val project: Project) {

    fun getApi(): ConnectApi = WithRestMarkDownGitlabConnectApi(
        GitlabConnectApi.from(
            pluginService().gitlabTokenConfiguration().host,
            pluginService().gitlabTokenConfiguration().token
        ),
        GitlabRestMarkDownProcessor(project.service<RestClientService>().get())
    )
}
