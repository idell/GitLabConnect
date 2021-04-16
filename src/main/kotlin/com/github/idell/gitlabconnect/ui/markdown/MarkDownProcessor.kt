package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo

interface MarkDownProcessor {
    fun process(issue: Issue,projectInfo: ProjectInfo): String
}
