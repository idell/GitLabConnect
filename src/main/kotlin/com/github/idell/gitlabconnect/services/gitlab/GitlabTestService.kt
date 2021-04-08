package com.github.idell.gitlabconnect.services.gitlab

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.intellij.openapi.components.Service

@Service
class GitlabTestService {

    fun test(host: String, token: String): Boolean {
        return try {
            GitlabConnectDataRetriever
                .from(host, token)
                .getCurrentUser()
                .isActive()
        } catch (e: GitlabConnectException) {
            false
        }
    }
}
