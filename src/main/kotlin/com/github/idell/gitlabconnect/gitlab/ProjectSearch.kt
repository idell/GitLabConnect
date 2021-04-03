package com.github.idell.gitlabconnect.gitlab

data class ProjectSearch(val name: String, val nestedNamespaces: List<String>) {

    fun pathWithNamespace(): String {
        if (nestedNamespaces.isEmpty()) {
            return name
        }

        return "${nestedNamespaces.last()}/$name"
    }

    fun fullPath(): String {
        if (nestedNamespaces.isEmpty()) {
            return name
        }

        return "${nestedNamespaces.joinToString("/")}/$name"
    }

    companion object {
        fun from(project: String): ProjectSearch {
            val list = project.split("/")
            if (list.size < 2) {
                return ProjectSearch(list.last(), emptyList())
            }
            return ProjectSearch(list.last(), list.subList(0, list.size - 1))
        }
    }
}
