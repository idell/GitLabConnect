package com.github.idell.gitlabconnect.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.URIish
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GitApiTestIT {

    @Test
    internal fun `given a git folder, retrieve information related remotes`() {

        val file = File("./exampleWithRemote")

        Git.init()
            .setDirectory(File(file.absoluteFile.toString() + "/.git"))
            .call()
            .remoteAdd()
            .setName("origin")
            .setUri(URIish("git@gitlab.example.com:namespace/project.git"))
            .call()

        val repository = FileRepositoryBuilder()
            .setGitDir(file)
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
