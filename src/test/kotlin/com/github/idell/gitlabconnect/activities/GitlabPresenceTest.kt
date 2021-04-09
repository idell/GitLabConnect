package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.Remote
import com.github.idell.gitlabconnect.git.VcsApi
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertTrue

internal class GitlabPresenceTest {

    private lateinit var vcsApi: VcsApi
    private lateinit var gitlabPresence: GitlabPresence

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        vcsApi = context.mock(VcsApi::class.java)
        gitlabPresence = GitlabPresence(vcsApi)
    }

    @Test
    internal fun `given a repository, when checking a correct gitlab remote, return it correctly`() {

        val expectedRemote = Remote("origin", "https://gitlab.myhost.com/subgroup/another/project.git")

        context.expecting {
            oneOf(vcsApi).findRemotes()
            will(returnValue(listOf(expectedRemote)))
        }

        gitlabPresence.evaluate("https://gitlab.myhost.com")
            .takeIf { it is GitlabAvailable }
            ?.let { assertThat(it).isEqualTo(GitlabAvailable(expectedRemote)) }
            ?: assertTrue { false }
    }

    @Test
    internal fun `given a repository without origin, when checking a remote, return the correct error`() {
        val expectedRemote = Remote("upstream", "https://gitlab.myhost.com/subgroup/another/project.git")

        context.expecting {
            oneOf(vcsApi).findRemotes()
            will(returnValue(listOf(expectedRemote)))
        }

        assertThat(gitlabPresence.evaluate("https://gitlab.anotherhost.com"))
            .isInstanceOf(GitRepositoryUnavailable::class.java)
    }

    private fun Mockery.expecting(block: Expectations.() -> Unit) {
        this.checking(Expectations().apply(block))
    }
}
