package com.github.idell.gitlabconnect.ui.markdown

import com.github.kittinunf.fuel.core.Request

interface RestClient {
    fun post(endpoint: String, jsonBody: String): Request
}


sealed class Response
object Fail : Response()
