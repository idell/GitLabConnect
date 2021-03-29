package com.github.idell.gitlabconnect.git

class GitRemote(val name: String, val address: String) {

    init {
        if (!address.matches(Regex(REGEX))) throw RuntimeException("address is not a valid git repository")
    }

    fun getRepositoryWithNamespace() : String {

        if (!address.contains("http")) {
            return address.substringAfter(":").removeSuffix(".git")
        }

        return address.substringAfter("//").substringAfter("/").removeSuffix(".git")
    }

    companion object {
        const val REGEX = "((git|ssh|http(s)?)|(git@[\\w\\.]+))(:(//)?)([\\w\\.@\\:/\\-~]+)"
    }
}

typealias GitRemotes = List<GitRemote>
