package com.github.idell.gitlabconnect.git

import org.eclipse.jgit.lib.ConfigConstants
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

class GitApi(private val repository: Repository) {

    fun findRemotes(): GitRemotes {
        return repository.remoteNames
            .map { GitRemote(it, getRemoteAddress(it)) }
            .toList()
    }

    private fun getRemoteAddress(remote: String) = repository.config.getString(
        ConfigConstants.CONFIG_REMOTE_SECTION,
        remote,
        ConfigConstants.CONFIG_KEY_URL
    )

    companion object {
        fun from(repository: String) : GitApi {
            return GitApi(
                FileRepositoryBuilder()
                    .setGitDir(File(repository))
                    .readEnvironment()
                    .findGitDir()
                    .setMustExist(true)
                    .build())
        }
    }
}