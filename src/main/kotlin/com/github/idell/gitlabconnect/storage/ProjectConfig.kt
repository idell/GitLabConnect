package com.github.idell.gitlabconnect.storage

import com.github.idell.gitlabconnect.storage.GitlabStatus.NOT_ANALYZED

data class ProjectConfig(var gitlabStatus: GitlabStatus = NOT_ANALYZED, var address: String = "noAddress")

enum class GitlabStatus {
    NOT_ANALYZED,
    GITLAB_PROJECT,
    GIT_NOT_FOUND,
}
