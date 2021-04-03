package com.github.idell.gitlabconnect.gitlab.issues

import com.github.idell.gitlabconnect.exception.GitlabConnectException
import com.github.idell.gitlabconnect.gitlab.ConnectApi
import com.github.idell.gitlabconnect.gitlab.ConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.gitlab.GitlabConnectApi
import com.github.idell.gitlabconnect.gitlab.GitlabConnectDataRetriever
import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.gitlab.ProjectSearch
import com.github.idell.gitlabconnect.gitlab.UserInfo
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
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.Optional
import kotlin.test.assertFalse
import org.gitlab4j.api.models.Issue as GitlabIssue

internal class GitlabConnectDataRetrieverTest {

    private lateinit var gitlabConnectApi: ConnectApi
    private lateinit var gitlabConnectDataRetriever: ConnectDataRetriever

    @RegisterExtension
    var context: Mockery = object : JUnit5Mockery() {
        init {
            setImposteriser(ClassImposteriser.INSTANCE)
        }
    }

    @BeforeEach
    internal fun setUp() {
        gitlabConnectApi = context.mock(ConnectApi::class.java)
        gitlabConnectDataRetriever = GitlabConnectDataRetriever(gitlabConnectApi)
    }

    @Test
    internal fun `given a full path project, returns a valid id`() {

        context.expecting {
            oneOf(gitlabConnectApi).search(EXPECTED_PROJECT)
            will(returnValue(generateProjects(EXPECTED_PROJECT, EXPECTED_ID)))
        }

        assertEquals(EXPECTED_ID, gitlabConnectDataRetriever.search(ProjectSearch.from(EXPECTED_PROJECT)).get().id)
    }

    @Test
    internal fun `given a wrong full path project, returns an empty `() {

        context.expecting {
            oneOf(gitlabConnectApi).search(WRONG_PROJECT)
            will(returnValue(generateProjects()))
        }

        assertEquals(Optional.empty<Int>(), gitlabConnectDataRetriever.search(ProjectSearch.from(WRONG_PROJECT)))
    }

    @Test
    internal fun `given a project id, returns his issues`() {

        context.expecting {
            oneOf(gitlabConnectApi).getIssues(ProjectInfo(1, "order-manager", "obi1"))
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

        val issues = gitlabConnectDataRetriever.getIssues(ProjectInfo(1, "order-manager", "obi1"))
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
            oneOf(gitlabConnectApi).getIssues(ProjectInfo(1, "order-manager", "obi1"))
            will(returnValue(emptyList<GitlabIssue>()))
        }

        val issues = gitlabConnectDataRetriever.getIssues(ProjectInfo(1, "order-manager", "obi1"))
        assertEquals(emptyList<Issue>(), issues)
    }

    @Test
    internal fun `given a gitlab configuration when retrieving user info return it correctly`() {
        context.expecting {
            oneOf(gitlabConnectApi).currentUser()
            will(returnValue(anUser("active")))
        }

        val actualUser = gitlabConnectDataRetriever.getCurrentUser()

        assertEquals(UserInfo(1, "John Doe", "active"), actualUser)
    }

    @Test
    internal fun `given a gitlab configuration, when retrieving user info, return an inactive user`() {
        context.expecting {
            oneOf(gitlabConnectApi).currentUser()
            will(returnValue(anUser("inactive")))
        }

        val actualUser = gitlabConnectDataRetriever.getCurrentUser()

        assertEquals(UserInfo(1, "John Doe", "inactive"), actualUser)
        assertFalse(actualUser.isActive())
    }

    @Test
    internal fun `given a gitlab configuration when retrieving user info throw exception due to unauthorized user`() {
        context.expecting {
            oneOf(gitlabConnectApi).currentUser()
            will(throwException(GitlabConnectException("an error")))
        }

        assertThrows<GitlabConnectException> { gitlabConnectDataRetriever.getCurrentUser() }
    }

    @Test
    @Disabled
    internal fun name() {
        val dataRetriever = GitlabConnectDataRetriever(
            GitlabConnectApi(
                GitlabTokenConfiguration(
                    "https://gitlab.lastminute.com",
                    "FPUDahkVWszzX4_wKTjD"
                )
            )
        )

        val project = dataRetriever.search(ProjectSearch.from("core-software/appfw-java/app-framework"))
        val get = project.get()
        println(get.name)
        val issues = dataRetriever.getIssues(get)
        println(issues.isEmpty())
    }

    private fun anUser(s: String): User {
        val user = User()
        user.name = "John Doe"
        user.id = 1
        user.state = s
        return user
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
