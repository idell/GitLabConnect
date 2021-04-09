package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.Remote
import com.github.idell.gitlabconnect.git.VcsApi
import com.github.idell.gitlabconnect.storage.GitlabStatus
import com.github.idell.gitlabconnect.storage.ProjectConfig
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class GitlabConnectAvailabilityTest {

    private lateinit var vcsApi: VcsApi

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        vcsApi = context.mock(VcsApi::class.java)
    }

    @Test
    internal fun `given a null vcs, return a project config without vcs`() {
        context.expecting { oneOf(vcsApi) }
        val gitlabConnectAvailability = GitlabConnectAvailability(vcsApi)

        assertThat(gitlabConnectAvailability.generateConfig("anHost"))
            .isEqualTo(ProjectConfig(GitlabStatus.GIT_NOT_FOUND))
    }

    @Test
    internal fun `given a vcs with a remote, when generating a config for another host, then return git not found`() {
        context.expecting {
            oneOf(vcsApi).findRemotes()
            will(
                returnValue(
                    listOf(Remote("origin", AN_ADDRESS))
                )
            )
        }

        val gitlabConnectAvailability = GitlabConnectAvailability(vcsApi)

        assertThat(gitlabConnectAvailability.generateConfig("https://anotherhost.com"))
            .isEqualTo(ProjectConfig(GitlabStatus.GIT_NOT_FOUND))
    }

    @Test
    internal fun `given a vcs with a remote, when generating a config for the same host, then return gitlab config`() {
        context.expecting {
            oneOf(vcsApi).findRemotes()
            will(
                returnValue(
                    listOf(Remote("origin", AN_ADDRESS))
                )
            )
        }

        val gitlabConnectAvailability = GitlabConnectAvailability(vcsApi)

        assertThat(gitlabConnectAvailability.generateConfig("https://anhost.com"))
            .isEqualTo(ProjectConfig(GitlabStatus.GITLAB_PROJECT, AN_ADDRESS))
    }

    private fun Mockery.expecting(block: Expectations.() -> Unit) {
        this.checking(Expectations().apply(block))
    }

    companion object {
        const val AN_ADDRESS = "https://gitlab.anhost.com/namespace/project.git"
    }
}
