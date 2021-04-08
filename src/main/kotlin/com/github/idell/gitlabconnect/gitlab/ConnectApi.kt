package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.models.Issue
import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User

interface ConnectApi {
    fun search(projectWithNamespace: GitlabProject): List<Project>
    fun getIssues(project: ProjectInfo): List<Issue>
    fun currentUser(): User
}
