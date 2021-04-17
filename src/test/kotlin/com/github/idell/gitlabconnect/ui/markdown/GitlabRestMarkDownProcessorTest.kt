package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.GlobalSettings
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.github.idell.gitlabconnect.storage.TokenStorage
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.Optional

@Disabled
internal class GitlabRestMarkDownProcessorTest {

    private lateinit var secureTokenStorage: TokenStorage
    private lateinit var globalSettings: GitlabConnectGlobalSettings
    private lateinit var markDownProcessor: GitlabRestMarkDownProcessor

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        secureTokenStorage = context.mock(TokenStorage::class.java)
        globalSettings = context.mock(GitlabConnectGlobalSettings::class.java)
        markDownProcessor = GitlabRestMarkDownProcessor(
            globalSettings,
            secureTokenStorage
        )
    }

    @Test
    internal fun process() {
        context.expecting {
            allowing(globalSettings).state
            will(
                returnValue(
                    GlobalSettings(
                        enabled = true,
                        tokenConfig = TokenConfiguration("anHost", "")
                    )
                )
            )

            allowing(secureTokenStorage).getToken("anHost")
            will(returnValue(Optional.of("aToken")))
        }
        val actual = markDownProcessor.process(
            Issue(
                "abc",
                "http",
                emptyList(),
                ""
            ),
            ProjectInfo(1, "", "")
        )
        assertThat(actual).isEqualTo("")
    }

    private fun Mockery.expecting(block: Expectations.() -> Unit) {
        this.checking(Expectations().apply(block))
    }
}
