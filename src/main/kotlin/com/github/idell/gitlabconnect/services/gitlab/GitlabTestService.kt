package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import com.intellij.openapi.components.Service
import org.gitlab4j.api.GitLabApiException

@Service
class GitlabTestService {

    fun test(host: String, token: String): Boolean {
        return try {
            GitlabConnectDataRetriever
                .from(host, token)
                .getCurrentUser()
                .isActive()
        } catch (e: GitLabApiException) {
            false
        }
    }

    fun test(config: TokenConfiguration): Boolean {
        return try {
            GitlabConnectDataRetriever
                .from(config.host, config.token)
                .getCurrentUser()
                .isActive()
        } catch (e: GitLabApiException) {
            false
        }
    }
}
