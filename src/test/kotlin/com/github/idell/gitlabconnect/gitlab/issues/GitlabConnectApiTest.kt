package com.github.idell.gitlabconnect.gitlab.issues

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.gitlab.*
import com.github.idell.gitlabconnect.gitlab.Issue
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.ProjectApi
import org.gitlab4j.api.models.Namespace
import org.gitlab4j.api.models.Project
import org.gitlab4j.api.models.User
import org.jmock.AbstractExpectations.returnValue
import org.jmock.AbstractExpectations.throwException
import org.jmock.Expectations
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.jmock.lib.legacy.ClassImposteriser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.*
import kotlin.test.assertFalse
import org.gitlab4j.api.models.Issue as GitlabIssue

internal class GitlabConnectApiTest {

    private lateinit var gitLabApi: GitLabApi
    private lateinit var connectApi: ConnectApi
    private lateinit var projects: ProjectApi

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        gitLabApi = context.mock(GitLabApi::class.java)
        projects = context.mock(ProjectApi::class.java)
        connectApi = GitlabConnectApi(gitLabApi)
    }

    @Test
    internal fun `given a full path project, returns a valid id`() {
        val projectSearch = ProjectSearch.from(EXPECTED_PROJECT)

        context.expecting {
            oneOf(gitLabApi).projectApi
            will(returnValue(projects))
            oneOf(projects).getProjects(projectSearch.pathWithNamespace())
            will(returnValue(generateProjects(EXPECTED_PROJECT, EXPECTED_ID)))
        }

        assertEquals(EXPECTED_ID, connectApi.search(projectSearch).get().id)
    }

    @Test
    internal fun `given a wrong full path project, returns an empty `() {
        val projectSearch = ProjectSearch.from(WRONG_PROJECT)

        context.expecting {
            oneOf(gitLabApi).projectApi
            will(returnValue(projects))
            oneOf(projects).getProjects(projectSearch.pathWithNamespace())
            will(returnValue(generateProjects()))
        }

        assertEquals(Optional.empty<Int>(), connectApi.search(projectSearch))
    }

/*    @Test
    internal fun `given a project id, returns his issues`() {

        context.expecting {
            oneOf(gitLabApi).getIssues(ProjectInfo(1, "order-manager", "obi1"))
            will(
                returnValue(
                    listOf(
                        anIssueWith(
                            "an issue", "an url",
                            listOf("a label", "a fancy label"),
                            "aDescription"
                        ),
                        anIssueWith(
                            "another issue", "another url",
                            listOf("another label"),
                            "aDescription"
                        )
                    )
                )
            )
        }

        val issues = connectApi.getIssues(ProjectInfo(1, "order-manager", "obi1"))
        assertEquals(
            listOf(
                Issue("an issue", "an url", listOf("a label", "a fancy label"), "aDescription"),
                Issue("another issue", "another url", listOf("another label"), "aDescription")
            ),
            issues
        )
    }

    @Test
    internal fun `given a project id, when it has no issues, returns an empty list`() {

        context.expecting {
            oneOf(gitLabApi).getIssues(ProjectInfo(1, "order-manager", "obi1"))
            will(returnValue(emptyList<GitlabIssue>()))
        }

        val issues = connectApi.getIssues(ProjectInfo(1, "order-manager", "obi1"))
        assertEquals(emptyList<Issue>(), issues)
    }

    @Test
    internal fun `given a gitlab configuration when retrieving user info return it correctly`() {
        context.expecting {
            oneOf(gitLabApi).currentUser()
            will(returnValue(anUser("active")))
        }

        val actualUser = connectApi.getCurrentUser()

        assertEquals(UserInfo(1, "John Doe", "active"), actualUser)
    }

    @Test
    internal fun `given a gitlab configuration, when retrieving user info, return an inactive user`() {
        context.expecting {
            oneOf(gitLabApi).currentUser()
            will(returnValue(anUser("inactive")))
        }

        val actualUser = connectApi.getCurrentUser()

        assertEquals(UserInfo(1, "John Doe", "inactive"), actualUser)
        assertFalse(actualUser.isActive())
    }

    @Test
    internal fun `given a gitlab configuration when retrieving user info throw exception due to unauthorized user`() {
        context.expecting {
            oneOf(gitLabApi).currentUser()
            will(throwException(GitlabConnectException("an error")))
        }

        assertThrows<GitlabConnectException> { connectApi.getCurrentUser() }
    }*/

    private fun anUser(s: String): User {
        val user = User()
        user.name = "John Doe"
        user.id = 1
        user.state = s
        return user
    }

    private fun anIssueWith(title: String, webUrl: String, labels: List<String>, description: String): GitlabIssue {
        val issue = GitlabIssue()
        issue.title = title
        issue.webUrl = webUrl
        issue.labels = labels
        issue.description = description
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
            val namespace = Namespace()
            namespace.name = "expectedNamespace"
            namespace.path = "expectedNamespace"

            expectedProject.pathWithNamespace = expectedPathWithNamespace
            expectedProject.id = expectedId
            expectedProject.name = "expectedName"
            expectedProject.path = "expectedName"
            expectedProject.namespace = namespace

            val anotherProject = Project()
            val anotherNamespace = Namespace()
            anotherNamespace.name = "anotherNamespace"
            anotherNamespace.path = "anotherNamespace"

            anotherProject.pathWithNamespace = "aNamespace/aProject"
            anotherProject.id = 123
            expectedProject.name = "anotherName"
            expectedProject.namespace = anotherNamespace

            return listOf(expectedProject, anotherProject)
        }

        fun Mockery.expecting(block: Expectations.() -> Unit) {
            this.checking(Expectations().apply(block))
        }
    }
}
