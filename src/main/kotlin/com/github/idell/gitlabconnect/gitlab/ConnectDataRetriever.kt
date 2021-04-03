package com.github.idell.gitlabconnect.gitlab

import java.util.*

interface ConnectDataRetriever {
    fun search(projectSearch: ProjectSearch): Optional<ProjectInfo>
    fun getIssues(project: ProjectInfo): Issues
    fun getCurrentUser(): UserInfo
}
