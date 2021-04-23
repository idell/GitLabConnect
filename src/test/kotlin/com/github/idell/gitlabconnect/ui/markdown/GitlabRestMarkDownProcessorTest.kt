package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.storage.TokenStorage
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.requests.DefaultRequest
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.URL

internal class GitlabRestMarkDownProcessorTest {

    private lateinit var secureTokenStorage: TokenStorage
    private lateinit var markDownProcessor: GitlabRestMarkDownProcessor
    private lateinit var restClient : RestClient

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        secureTokenStorage = context.mock(TokenStorage::class.java)
        restClient = context.mock(RestClient::class.java)
        markDownProcessor = GitlabRestMarkDownProcessor(restClient)
    }

    @Test
    internal fun process() {
        val request = DefaultRequest(Method.POST, URL("anHost/api/v4/markdown"))

        context.expecting {
            allowing(restClient).post("api/v4/markdown")
            with(returnValue(request))
        }
        val actual = markDownProcessor.process(
            Issue(
                "abc",
                "http",
                emptyList(),
                ""
            )
        )
        assertThat(actual).isEqualTo("")
    }

    private fun Mockery.expecting(block: Expectations.() -> Unit) {
        this.checking(Expectations().apply(block))
    }

}
