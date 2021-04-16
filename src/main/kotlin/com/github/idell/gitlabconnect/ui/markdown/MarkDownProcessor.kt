package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue

interface MarkDownProcessor {
    fun process(issue:Issue) : String
}
