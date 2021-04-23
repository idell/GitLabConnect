package com.github.idell.gitlabconnect.services.restclient

import com.github.idell.gitlabconnect.services.gitlab.GitlabConfigService
import com.github.idell.gitlabconnect.utils.restclient.FuelGitlabConnectRestClient
import com.github.idell.gitlabconnect.utils.restclient.GitlabConnectRestClient
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service
class RestClientService(project: Project) {

    private var fuelGitlabConnectRestClient: GitlabConnectRestClient =
        FuelGitlabConnectRestClient(project.service<GitlabConfigService>().get())

    fun get(): GitlabConnectRestClient {
        return fuelGitlabConnectRestClient
    }
}