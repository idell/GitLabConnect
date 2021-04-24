package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.utils.restclient.Failure
import com.github.idell.gitlabconnect.utils.restclient.GitlabConnectRestClient
import com.github.idell.gitlabconnect.utils.restclient.Success
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GitlabRestMarkDownProcessor(private val gitlabConnectRestClient: GitlabConnectRestClient) : MarkDownProcessor {

    override fun process(issue: Issue): String {
        val markdownRequest = MarkdownRequest.from(issue)

        return when (val gitlabRestResponse = gitlabConnectRestClient.post(MARKDOWN_ENDPOINT,
                                                                           Gson().toJson(markdownRequest))) {
            is Success -> Gson().fromJson(gitlabRestResponse.body, MarkdownResponse::class.java).html
            is Failure ->
                markdownRequest.text
                    .also { LOGGER.warn(GitlabConnectBundle.message(REST_CALL_ERROR_MESSAGE,
                                                                    GITLAB,
                                                                    gitlabRestResponse.throwable)) }
        }
    }

    companion object {
        private const val MARKDOWN_ENDPOINT = "api/v4/markdown"
        private const val REST_CALL_ERROR_MESSAGE = "rest.call.error"
        private const val GITLAB = "Gitlab"
        private val LOGGER: Logger = LoggerFactory.getLogger(GitlabRestMarkDownProcessor::class.java)
    }
}

data class MarkdownResponse(val html: String)
data class MarkdownRequest(val text: String, val gfm: Boolean = true) {
    companion object {
        fun from(issue: Issue): MarkdownRequest {
            return MarkdownRequest("# ${issue.title} \n\n --- \n ${issue.description}")
        }
    }
}
