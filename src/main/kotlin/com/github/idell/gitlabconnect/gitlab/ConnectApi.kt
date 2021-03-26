package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.GitLabApi

interface ConnectApi {
    fun getGitlabApi(): GitLabApi
}