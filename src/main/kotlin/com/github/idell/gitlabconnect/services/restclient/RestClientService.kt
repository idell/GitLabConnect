package com.github.idell.gitlabconnect.services.restclient

import com.github.idell.gitlabconnect.services.gitlab.GitlabConfigService
import com.github.idell.gitlabconnect.utils.restclient.FuelGitlabConnectRestClient
import com.github.idell.gitlabconnect.utils.restclient.GitlabConnectRestClient
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service
class RestClientService(private val project: Project) {

    fun get(): GitlabConnectRestClient {
        return FuelGitlabConnectRestClient(project.service<GitlabConfigService>().get())
    }
}
