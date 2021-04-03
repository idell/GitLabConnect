package com.github.idell.gitlabconnect.gitlab

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProjectSearchTest {

    @Test
    internal fun `given a project without namespaces, then return a correct search info`() {
        val projectSearch = ProjectSearch.from("aProject")

        assertEquals("aProject", projectSearch.name)
        assertEquals("aProject", projectSearch.pathWithNamespace())
        assertEquals("aProject", projectSearch.fullPath())
    }

    @Test
    internal fun `given a project with namespace, then return a correct search info`() {
        val projectSearch = ProjectSearch.from("aNamespace/aProject")

        assertEquals("aProject", projectSearch.name)
        assertEquals("aNamespace/aProject", projectSearch.pathWithNamespace())
        assertEquals("aNamespace/aProject", projectSearch.fullPath())
    }

    @Test
    internal fun `given a project with namespace and a group, then return a correct search info`() {
        val projectSearch = ProjectSearch.from("aGroup/aNamespace/aProject")

        assertEquals("aProject", projectSearch.name)
        assertEquals("aNamespace/aProject", projectSearch.pathWithNamespace())
        assertEquals("aGroup/aNamespace/aProject", projectSearch.fullPath())
    }

    @Test
    internal fun `given a project with namespace, group and subgroup, then return a correct search info`() {
        val projectSearch = ProjectSearch.from("aSubGroup/aGroup/aNamespace/aProject")

        assertEquals("aProject", projectSearch.name)
        assertEquals("aNamespace/aProject", projectSearch.pathWithNamespace())
        assertEquals("aSubGroup/aGroup/aNamespace/aProject", projectSearch.fullPath())
    }
}
