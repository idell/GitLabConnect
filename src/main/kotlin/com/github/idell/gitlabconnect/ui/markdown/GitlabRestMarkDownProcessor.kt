package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.apache.http.protocol.HTTP
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitlabRestMarkDownProcessor(
    private val globalSettings: GitlabConnectGlobalSettings,
    private val tokenStorage: SecureTokenStorage
) : MarkDownProcessor {

    override fun process(issue: Issue,projectInfo: ProjectInfo): String {
        val (enabled, tokenConfig) = globalSettings.state
        if (!enabled) return appendDescriptionToTitle(issue)

        val token: String = tokenStorage
            .getToken(tokenConfig.host)
            .orElseThrow {
                GitlabProcessException(GitlabConnectBundle.message(TOKEN_NOT_FOUND_MESSAGE, tokenConfig.host))
            }

        val (_, response, result) =
            composeEndpoint(tokenConfig)
                .httpPost()
                .useHttpCache(USE_HTTP_CACHES)
                .header(
                    HTTP.CONTENT_TYPE to JSON,
                    PRIVATE_TOKEN to token
                )
                .body(
                    Gson().toJson(
                        Payload(
                            appendDescriptionToTitle(issue),
                            project = "${projectInfo.namespace}/${projectInfo.name}"
                        )
                    )
                )
                .response()

        return when (result) {
            is Result.Success -> Gson().fromJson(response.body().asString(JSON), Response::class.java).html
            is Result.Failure -> appendDescriptionToTitle(issue)
                .also { LOGGER.warn("Error calling gitlab: ${result.error}") }
        }
    }

    private fun composeEndpoint(tokenConfig: TokenConfiguration): String {
        return when {
            tokenConfig.host.endsWith("/") -> "${tokenConfig.host}$MARKDOWN_ENDPOINT"
            else -> "${tokenConfig.host}/$MARKDOWN_ENDPOINT"
        }
    }

    private fun appendDescriptionToTitle(issue: Issue): String =
        "# ${issue.title} \n\n --- \n ${issue.description}"

    companion object {
        private const val USE_HTTP_CACHES = true
        private const val JSON = "application/json"
        private const val MARKDOWN_ENDPOINT = "api/v4/markdown"
        private const val PRIVATE_TOKEN = "Private-Token"
        private const val TOKEN_NOT_FOUND_MESSAGE = "storage.token.not-found"
        private val LOGGER: Logger = LoggerFactory.getLogger(GitlabRestMarkDownProcessor::class.java)
    }
}

data class Payload(val text: String, val gfm: Boolean = true, val project: String)

data class Response(val html: String)
