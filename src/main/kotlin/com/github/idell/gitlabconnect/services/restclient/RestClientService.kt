package com.github.idell.gitlabconnect.services.restclient

import com.github.idell.gitlabconnect.services.configuration.GitlabConnectConfigurationApplicationService.Companion.gitlabConnectConfigurationApplicationService
import com.github.idell.gitlabconnect.utils.restclient.FuelGitlabConnectRestClient
import com.github.idell.gitlabconnect.utils.restclient.GitlabConnectRestClient
import com.intellij.openapi.components.Service

@Service
class RestClientService {

    fun get(): GitlabConnectRestClient {
        return FuelGitlabConnectRestClient(gitlabConnectConfigurationApplicationService().gitlabTokenConfiguration())
    }
}
