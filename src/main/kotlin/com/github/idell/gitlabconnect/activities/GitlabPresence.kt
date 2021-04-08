package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.Remote
import com.github.idell.gitlabconnect.git.VcsApi
import com.github.idell.gitlabconnect.git.findOrigin

class GitlabPresence(private val vcsApi: VcsApi) {

    fun evaluate(globalHost: String): GitlabAvailability =
        vcsApi.findRemotes()
            .findOrigin()
            ?.takeIf { it.belongTo(globalHost) }
            ?.let { GitlabAvailable(it) }
            ?: GitRepositoryUnavailable
}

sealed class GitlabAvailability

data class GitlabAvailable(val remote: Remote) : GitlabAvailability()
object GitRepositoryUnavailable : GitlabAvailability()
