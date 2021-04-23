package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.storage.TokenStorage
import com.github.idell.gitlabconnect.utils.restclient.Failure
import com.github.idell.gitlabconnect.utils.restclient.GitlabConnectRestClient
import com.github.idell.gitlabconnect.utils.restclient.Success
import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class GitlabRestMarkDownProcessorTest {

    private lateinit var secureTokenStorage: TokenStorage
    private lateinit var markDownProcessor: GitlabRestMarkDownProcessor
    private lateinit var gitlabConnectRestClient : GitlabConnectRestClient

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        secureTokenStorage = context.mock(TokenStorage::class.java)
        gitlabConnectRestClient = context.mock(GitlabConnectRestClient::class.java)
        markDownProcessor = GitlabRestMarkDownProcessor(gitlabConnectRestClient)
    }

    @Test
    internal fun aSuccessRequest() {
        val issue = Issue(
            "abc",
            "http",
            emptyList(),
            ""
        )

        context.expecting {
            oneOf(gitlabConnectRestClient).post("api/v4/markdown", Gson().toJson(MarkdownRequest.from(issue)))
            will(returnValue(Success(Gson().toJson(MarkdownResponse("<p>My html</p>")))))
        }

        assertThat(markDownProcessor.process(issue)).isEqualTo("<p>My html</p>")
    }

    @Test
    internal fun aFailedRequest() {
        val issue = Issue(
            "abc",
            "http",
            emptyList(),
            ""
        )

        context.expecting {
            oneOf(gitlabConnectRestClient).post("api/v4/markdown", Gson().toJson(MarkdownRequest.from(issue)))
            will(returnValue(Failure(RuntimeException())))
        }

        assertThat(markDownProcessor.process(issue)).isEqualTo(MarkdownRequest.from(issue).text)
    }

    private fun Mockery.expecting(block: Expectations.() -> Unit) {
        this.checking(Expectations().apply(block))
    }

}
