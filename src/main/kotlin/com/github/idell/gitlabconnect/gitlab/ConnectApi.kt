package com.github.idell.gitlabconnect.gitlab

import java.util.Optional

interface ConnectApi {
    fun search(projectSearch: ProjectSearch): Optional<ProjectInfo>
    fun getIssues(project: ProjectInfo): Issues
    fun getCurrentUser(): UserInfo
    fun markdownApi(issue: Issue): String
}
