package com.github.idell.gitlabconnect.gitlab

typealias Issues = List<Issue>

data class Issue(val title: String, val link: String, val labels: List<String>, val description: String)
