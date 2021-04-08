package com.github.idell.gitlabconnect.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.URIish
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GitApiTestIT {

    private val exampleWithRemotes: File = File("./exampleWithRemote")
    private val exampleWithoutRemotes = File("src/test/resources/exampleWithoutRemote")

    @BeforeEach
    internal fun setUp() {
        createRepository(exampleWithRemotes, "origin", "git@gitlab.example.com:namespace/project.git")
        Git.init()
            .setDirectory(exampleWithoutRemotes)
            .call()
    }

    @AfterEach
    internal fun tearDown() {
        exampleWithRemotes.deleteRecursively()
        exampleWithoutRemotes.deleteRecursively()
    }

    private fun createRepository(file: File, remoteName: String, remoteUrl: String) {
        Git.init()
            .setDirectory(file)
            .call()
            .remoteAdd()
            .setName(remoteName)
            .setUri(URIish(remoteUrl))
            .call()
    }

    @Test
    internal fun `given a git folder, retrieve information related remotes`() {

        val repository = FileRepositoryBuilder()
            .setGitDir(File(exampleWithRemotes.absoluteFile.toString() + "/.git"))
            .readEnvironment()
            .findGitDir()
            .setMustExist(true)
            .build()

        val remotes = GitApi(repository).findRemotes()

        assertEquals("origin", remotes[0].name)
        assertEquals("git@gitlab.example.com:namespace/project.git", remotes[0].address)
    }

    @Test
    internal fun `given a git folder without remotes, return an empty list`() {

        val repository = FileRepositoryBuilder()
            .setGitDir(File(exampleWithoutRemotes.absoluteFile.toString() + "/.git"))
            .readEnvironment()
            .findGitDir()
            .setMustExist(true)
            .build()

        val gitApi = GitApi(repository)
        val remotes = gitApi.findRemotes()

        assertTrue { remotes.isEmpty() }
    }

}
