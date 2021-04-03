package com.github.idell.gitlabconnect.gitlab.issues

import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.Issue
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.IssuesApi
import org.gitlab4j.api.ProjectApi
import org.gitlab4j.api.models.Project
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.*
import org.gitlab4j.api.models.Issue as GitlabIssue

internal class GitlabConnectDataRetrieverTest {

    private lateinit var projectApi: ProjectApi
    private lateinit var issuesApi: IssuesApi
    private lateinit var gitLabApi: GitLabApi
    private lateinit var gitlabConnectApi: ConnectApi
    private lateinit var gitlabConnectDataRetriever: GitlabConnectDataRetriever

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        gitlabConnectApi = context.mock(ConnectApi::class.java)
        gitLabApi = context.mock(GitLabApi::class.java)
        projectApi = context.mock(ProjectApi::class.java)
        issuesApi = context.mock(IssuesApi::class.java)
        gitlabConnectDataRetriever = GitlabConnectDataRetriever(gitlabConnectApi)
    }

    @Test
    internal fun `given a full path project, returns a valid id`() {

        context.expecting {
            oneOf(gitlabConnectApi).getGitlabApi()
            will(returnValue(gitLabApi))
            oneOf(gitLabApi).projectApi
            will(returnValue(projectApi))
            oneOf(projectApi).getProjects(EXPECTED_PROJECT)
            will(returnValue(generateProjects(EXPECTED_PROJECT, EXPECTED_ID)))
        }


        assertEquals(EXPECTED_ID, gitlabConnectDataRetriever.getId(EXPECTED_PROJECT).get())
    }

    @Test
    internal fun `given a wrong full path project, returns an empty `() {

        context.expecting {
            oneOf(gitlabConnectApi).getGitlabApi()
            will(returnValue(gitLabApi))
            oneOf(gitLabApi).projectApi
            will(returnValue(projectApi))
            oneOf(projectApi).getProjects(WRONG_PROJECT)
            will(returnValue(generateProjects()))
        }

        assertEquals(Optional.empty<Int>(), gitlabConnectDataRetriever.getId(WRONG_PROJECT))
    }

    @Test
    internal fun `given a project id, returns his issues`() {

        context.expecting {
            oneOf(gitlabConnectApi).getGitlabApi()
            will(returnValue(gitLabApi))
            oneOf(gitLabApi).issuesApi
            will(returnValue(issuesApi))
            oneOf(issuesApi).getIssues(EXPECTED_PROJECT)
            will(
                returnValue(
                    listOf(
                        anIssueWith(
                            "an issue", "an url",
                            listOf("a label", "a fancy label")
                        ),
                        anIssueWith(
                            "another issue", "another url",
                            listOf("another label")
                        )
                    )
                )
            )
        }

        val gitlabConnectDataRetriever = GitlabConnectDataRetriever(gitlabConnectApi)
        val issues = gitlabConnectDataRetriever.getIssues(EXPECTED_PROJECT)
        assertEquals(
            listOf(
                Issue("an issue", "an url", listOf("a label", "a fancy label")),
                Issue("another issue", "another url", listOf("another label"))
            ),
            issues
        )
    }

    @Test
    internal fun `given a project id, when it has no issues, returns an empty list`() {

        context.expecting {
            oneOf(gitlabConnectApi).getGitlabApi()
            will(returnValue(gitLabApi))
            oneOf(gitLabApi).issuesApi
            will(returnValue(issuesApi))
            oneOf(issuesApi).getIssues(EXPECTED_PROJECT)
            will(returnValue(emptyList<GitlabIssue>()))
        }

        val issues = gitlabConnectDataRetriever.getIssues(EXPECTED_PROJECT)
        assertEquals(emptyList<Issue>(), issues)
    }

    private fun anIssueWith(title: String, webUrl: String, labels: List<String>): GitlabIssue {
        val issue = GitlabIssue()
        issue.title = title
        issue.webUrl = webUrl
        issue.labels = labels
        return issue
    }

    companion object {

        private const val EXPECTED_PROJECT = "obi1/order-manager"
        private const val WRONG_PROJECT = "myWrongNamespace/wrongProject"
        private const val EXPECTED_ID = 2455

        fun generateProjects(): List<Project> {
            return generateProjects("someNamespace", 145)
        }

        fun generateProjects(expectedPathWithNamespace: String, expectedId: Int): List<Project> {
            val expectedProject = Project()
            expectedProject.pathWithNamespace = expectedPathWithNamespace
            expectedProject.id = expectedId

            val anotherProject = Project()
            anotherProject.pathWithNamespace = "aNamespace/aProject"
            anotherProject.id = 123

            return listOf(expectedProject, anotherProject)
        }

        fun Mockery.expecting(block: Expectations.() -> Unit) {
            this.checking(Expectations().apply(block))
        }
    }
}
