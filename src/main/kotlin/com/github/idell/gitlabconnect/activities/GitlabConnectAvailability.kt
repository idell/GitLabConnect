package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.VcsApi
import com.github.idell.gitlabconnect.storage.GitlabStatus
import com.github.idell.gitlabconnect.storage.ProjectConfig

class GitlabConnectAvailability(private val vcsApi: VcsApi?) {

    fun generateConfig(globalHost: String): ProjectConfig =
        vcsApi
            ?.let { evaluate(it, globalHost) }
            ?: ProjectConfig(GitlabStatus.GIT_NOT_FOUND)

    private fun evaluate(vcsApi: VcsApi, host: String): ProjectConfig {
        return when (val availability = GitlabPresence(vcsApi).evaluate(host)) {
            is GitlabAvailable -> ProjectConfig(GitlabStatus.GITLAB_PROJECT, availability.remote.address)
            GitRepositoryUnavailable -> ProjectConfig(GitlabStatus.GIT_NOT_FOUND)
        }
    }
}
