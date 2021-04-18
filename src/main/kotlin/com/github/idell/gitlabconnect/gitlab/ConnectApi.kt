package com.github.idell.gitlabconnect.gitlab

import org.gitlab4j.api.models.Issue as GitlabIssue
import org.gitlab4j.api.models.Project as GitlabApiProject
import org.gitlab4j.api.models.User as GitlabApiUser

interface ConnectApi {
    fun search(projectWithNamespace: GitlabProject): List<GitlabApiProject>
    fun getIssues(project: ProjectInfo): List<GitlabIssue>
    fun currentUser(): GitlabApiUser
    fun markdownApi(issue: Issue): String
}
