package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.intellij.openapi.components.ServiceManager

class GitlabTestConnectionService {
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

    fun test(config: GitlabTokenConfiguration): Boolean {
        return try {
            GitlabConnectApi(config)
                .getCurrentUser()
                .isActive()
        } catch (e: GitlabConnectException) {
            false
        }
    }

    companion object {
        @JvmStatic
        fun gitlabTestConnectionService(): GitlabTestConnectionService = ServiceManager
            .getService(GitlabTestConnectionService::class.java)
    }
}
