package com.github.idell.gitlabconnect.utils.restclient

import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import org.slf4j.LoggerFactory

class FuelGitlabConnectRestClient(private val gitlabConfiguration: GitlabTokenConfiguration) : GitlabConnectRestClient {

    override fun post(endpoint: String, jsonBody: String): GitlabRestResponse {

        val path = "${gitlabConfiguration.host}/$endpoint"
        val (_, response, result) = Fuel.post(path)
            .header(PRIVATE_TOKEN to gitlabConfiguration.token)
            .useHttpCache(USE_HTTP_CACHES)
            .timeout(DEFAULT_TIMEOUT)
            .jsonBody(jsonBody)
            .response()
        also { LOGGER.info("#debug Calling gitlab with: [${gitlabConfiguration.token}] to path [$path]") }

        return when (result) {
            is Result.Success -> Success(response.body().asString(JSON))
            is Result.Failure -> Failure(result.error)
        }
    }

    companion object {
        private const val JSON = "application/json"
        private const val USE_HTTP_CACHES = true
        private const val PRIVATE_TOKEN = "Private-Token"
        private const val DEFAULT_TIMEOUT = 1000
        private val LOGGER = LoggerFactory.getLogger(FuelGitlabConnectRestClient::class.java)
    }
}
