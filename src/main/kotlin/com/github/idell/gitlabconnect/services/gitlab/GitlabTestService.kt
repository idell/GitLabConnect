package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.intellij.openapi.components.Service

@Service
class GitlabTestService {
    fun test(host: String, token: String): Boolean {
        return try {
            GitlabConnectApi
                .from(host, token)
                .getCurrentUser()
                .isActive()
        } catch (e: GitlabConnectException) {
            false
        }
    }

    fun test(config: TokenConfiguration): Boolean {
        return try {
            GitlabConnectApi
                .from(config.host, config.token)
                .getCurrentUser()
                .isActive()
        } catch (e: GitlabConnectException) {
            false
        }
    }
}
