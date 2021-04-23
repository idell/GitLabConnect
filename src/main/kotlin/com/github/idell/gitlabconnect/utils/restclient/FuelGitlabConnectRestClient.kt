package com.github.idell.gitlabconnect.utils.restclient

import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result

class FuelGitlabConnectRestClient(private val gitlabConfiguration: GitlabTokenConfiguration) : GitlabConnectRestClient {

    override fun post(endpoint: String,jsonBody:String): GitlabRestResponse {

        val (_, response, result) = Fuel.post("${gitlabConfiguration.host}/${endpoint}")
            .header(PRIVATE_TOKEN to gitlabConfiguration.token)
            .useHttpCache(USE_HTTP_CACHES)
            .timeout(DEFAULT_TIMEOUT)
            .jsonBody(jsonBody)
            .response()

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
    }


}