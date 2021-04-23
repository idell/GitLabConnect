package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.RequestFactory
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitlabRestMarkDownProcessor(private val restClient: RestClient) : MarkDownProcessor {

    override fun process(issue: Issue): String {

        val (_, response, result) = restClient
            .post(MARKDOWN_ENDPOINT)
            .jsonBody(Gson().toJson(Payload(appendDescriptionToTitle(issue))))
            .response()

        return when (result) {
            is Result.Success -> Gson().fromJson(response.body().asString(JSON), Response::class.java).html
            is Result.Failure -> appendDescriptionToTitle(issue)
                .also { LOGGER.warn(GitlabConnectBundle.message(REST_CALL_ERROR_MESSAGE, GITLAB, result.error)) }
        }
    }

    private fun appendDescriptionToTitle(issue: Issue): String =
        "# ${issue.title} \n\n --- \n ${issue.description}"

    companion object {
        private const val JSON = "application/json"
        private const val MARKDOWN_ENDPOINT = "api/v4/markdown"
        private const val REST_CALL_ERROR_MESSAGE = "rest.call.error"
        private const val GITLAB = "Gitlab"
        private val LOGGER: Logger = LoggerFactory.getLogger(GitlabRestMarkDownProcessor::class.java)
    }
}

data class Payload(val text: String, val gfm: Boolean = true)

data class Response(val html: String)
