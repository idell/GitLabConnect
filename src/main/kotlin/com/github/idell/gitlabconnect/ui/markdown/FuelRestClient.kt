package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody

class FuelRestClient(private val gitlabConfiguration: GitlabTokenConfiguration) : RestClient {
    override fun post(endpoint: String,jsonBody:String): Request =
        Fuel.post("${gitlabConfiguration.host}/${endpoint}")
            .header(PRIVATE_TOKEN to gitlabConfiguration.token)
            .useHttpCache(USE_HTTP_CACHES)
            .timeout(DEFAULT_TIMEOUT)
            .jsonBody(jsonBody)


    companion object{
        private const val USE_HTTP_CACHES = true
        private const val PRIVATE_TOKEN = "Private-Token"
        private const val DEFAULT_TIMEOUT = 1000
    }


}