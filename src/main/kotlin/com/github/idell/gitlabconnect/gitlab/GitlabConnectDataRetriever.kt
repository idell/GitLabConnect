package com.github.idell.gitlabconnect.gitlab

import java.util.Optional

interface ConnectDataRetriever {
    fun getId(pathWitNamespace: String): Optional<Int>
    fun getIssues(projectId: String): Issues
}

typealias Issues = List<Issue>

data class Issue(val title: String, val link: String, val labels: List<String>)
