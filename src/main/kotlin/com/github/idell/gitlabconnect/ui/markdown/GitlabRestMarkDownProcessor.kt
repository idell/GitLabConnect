package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.git.Remote
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.storage.ProjectConfig
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.apache.http.protocol.HTTP
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitlabRestMarkDownProcessor(
    private val gitlabTokenConfiguration: GitlabTokenConfiguration,
    private val projectSupplier: () -> ProjectConfig
) : MarkDownProcessor {

    override fun process(issue: Issue, projectInfo: ProjectInfo): String {

        val (_, address) = projectSupplier.invoke()
        val info: Remote = try {
            Remote("origin", address)
        } catch (e: GitlabConnectException) {
            LOGGER.warn("Error obtaining git remote", e)
            // TODO ivn remove this stub (maybe could be useful to get a default project or a
            //  "random" project in the remotes)
            Remote("origin", "git@gitlab.lastminute.com:team-commander/rumba.git")
        }

        val (_, response, result) =
            composeEndpoint(gitlabTokenConfiguration)
                .httpPost()
                .timeout(DEFAULT_TIMEOUT)
                .useHttpCache(USE_HTTP_CACHES)
                .header(
                    HTTP.CONTENT_TYPE to JSON,
                    PRIVATE_TOKEN to gitlabTokenConfiguration.token
                )
                .body(
                    Gson().toJson(
                        Payload(
                            text = appendDescriptionToTitle(issue),
                            project = info.getRepositoryWithNamespace()
                        )
                    )
                )
                .response()

        return when (result) {
            is Result.Success -> Gson().fromJson(response.body().asString(JSON), Response::class.java).html
            is Result.Failure -> appendDescriptionToTitle(issue)
                .also { LOGGER.warn(GitlabConnectBundle.message(REST_CALL_ERROR_MESSAGE, GITLAB, result.error)) }
        }
    }

    private fun composeEndpoint(tokenConfig: GitlabTokenConfiguration): String {
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
        private const val REST_CALL_ERROR_MESSAGE = "rest.call.error"
        private const val GITLAB = "Gitlab"
        private const val DEFAULT_TIMEOUT = 1000
        private val LOGGER: Logger = LoggerFactory.getLogger(GitlabRestMarkDownProcessor::class.java)
    }
}

data class Payload(val text: String, val gfm: Boolean = true, val project: String)

data class Response(val html: String)
