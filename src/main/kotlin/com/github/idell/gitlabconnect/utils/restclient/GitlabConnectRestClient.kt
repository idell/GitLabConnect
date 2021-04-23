package com.github.idell.gitlabconnect.utils.restclient

interface GitlabConnectRestClient {
    fun post(endpoint: String, jsonBody: String): GitlabRestResponse
}

sealed class GitlabRestResponse
data class Failure(val throwable: Throwable): GitlabRestResponse()
data class Success(val body: String): GitlabRestResponse()
