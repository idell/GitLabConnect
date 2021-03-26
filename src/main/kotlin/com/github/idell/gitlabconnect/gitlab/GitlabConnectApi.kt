package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.GitLabApi

class GitlabConnectApi(gitlabConfiguration: GitlabConfiguration): ConnectApi {
    private val gitLabApi = GitLabApi(gitlabConfiguration.host, gitlabConfiguration.token)

    override fun getGitlabApi(): GitLabApi {
        return gitLabApi
    }
}