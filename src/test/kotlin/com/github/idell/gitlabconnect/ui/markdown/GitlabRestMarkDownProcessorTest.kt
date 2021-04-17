package com.github.idell.gitlabconnect.ui.markdown

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GitlabRestMarkDownProcessorTest {
    @Test
    internal fun process() {

        val actual = GitlabRestMarkDownProcessor(
            GitlabConnectGlobalSettings.getInstance(),
            SecureTokenStorage()
        ).process(
            Issue(
                "abc",
                "http",
                emptyList(),
                ""
            ),
            ProjectInfo(1, "", "")
        )
        assertThat(actual).isEqualTo("")
    }
}
