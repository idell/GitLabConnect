package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue

@Deprecated("Do not use this until https://github.com/gitlab4j/gitlab4j-api/issues/687 is not solved")
class GitlabApiMarkDownProcessor : MarkDownProcessor {
    override fun process(issue: Issue): String {
        TODO()
    }
}
