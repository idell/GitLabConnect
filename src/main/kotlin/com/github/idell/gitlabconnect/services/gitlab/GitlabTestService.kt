package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.intellij.openapi.components.Service

@Service
class GitlabTestService {

    fun test(host: String, token: String): Boolean {
        return try {
            GitlabConnectDataRetriever
                .from(host, token)
                .getCurrentUser()
                .isActive()
        } catch (e: Exception) {
            false
        }
    }

    fun test(config: TokenConfiguration): Boolean {
        return try {
            GitlabConnectDataRetriever
                .from(config.host, config.token)
                .getCurrentUser()
                .isActive()
        } catch (e: Exception) {
            false
        }
    }
}
