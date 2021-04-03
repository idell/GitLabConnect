package com.github.idell.gitlabconnect.git

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GitRemoteTest {

    @Test
    internal fun `given a valid ssh git remote, retrieve namespace with project`() {
        val gitRemote = GitRemote("origin", "git@gitlab.example.com:namespace/project.git")

        assertEquals("namespace/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `given a valid ssh git remote without suffix, retrieve namespace with project`() {
        val gitRemote = GitRemote("origin", "git@gitlab.example.com:namespace/project")

        assertEquals("namespace/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `given a valid ssh git remote, retrieve another namespace with project`() {
        val gitRemote = GitRemote("origin", "git@gitlab.example.com:another/project.git")

        assertEquals("another/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `given a valid http remote, retrieve namespace and project`() {
        val gitRemote = GitRemote("origin", "https://example.lastminute.com/namespace/project.git")

        assertEquals("namespace/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `given a valid http remote without suffix, retrieve namespace and project`() {
        val gitRemote = GitRemote("origin", "https://example.lastminute.com/namespace/project")

        assertEquals("namespace/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `given a valid http remote, retrieve another namespace and project`() {
        val gitRemote = GitRemote("origin", "https://example.lastminute.com/another/project.git")

        assertEquals("another/project", gitRemote.getRepositoryWithNamespace())
    }

    @Test
    internal fun `check if an address is a valid git repository`() {
        assertThrows(GitlabConnectException::class.java) { GitRemote("origin", "$$$$$") }
    }
}
