package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.exception.GitlabProcessException
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service
class GitlabConfigService(val project: Project) {

    private val tokenStorage = SecureTokenStorage()

    fun get(): GitlabTokenConfiguration {
        val (_, tokenConfig) = GitlabConnectGlobalSettings.get(project)

        val token = tokenStorage.getToken(tokenConfig.host).orElseThrow {
            GitlabProcessException(GitlabConnectBundle.message(TOKEN_NOT_FOUND_MESSAGE, tokenConfig.host))
        }
        return GitlabTokenConfiguration(tokenConfig.host, token)
    }

    companion object {
        private const val TOKEN_NOT_FOUND_MESSAGE = "storage.token.not-found"
    }
}