package com.github.idell.gitlabconnect.git

import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GitApiTestIT {

    @Test
    internal fun `given a git folder, retrieve information related remotes`() {

        val repository = FileRepositoryBuilder()
            .setGitDir(File("src/test/resources/exampleWithRemote"))
            .readEnvironment()
            .findGitDir()
            .setMustExist(true)
            .build()

        val gitApi = GitApi(repository)
        val remotes = gitApi.findRemotes()

        assertEquals("origin", remotes[0].name)
        assertEquals("git@gitlab.example.com:namespace/project.git", remotes[0].address)
    }

    @Test
    internal fun `given a git folder without remotes, return an empty list`() {

        val repository = FileRepositoryBuilder()
            .setGitDir(File("src/test/resources/exampleWithoutRemote"))
            .readEnvironment()
            .findGitDir()
            .setMustExist(true)
            .build()

        val gitApi = GitApi(repository)
        val remotes = gitApi.findRemotes()

        assertTrue { remotes.isEmpty() }
    }

}