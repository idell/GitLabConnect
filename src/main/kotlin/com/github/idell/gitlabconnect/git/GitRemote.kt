package com.github.idell.gitlabconnect.git

import com.github.idell.gitlabconnect.exception.GitlabConnectException

class GitRemote(val name: String, val address: String) {

    init {
        if (!address.matches(Regex(REGEX))) throw GitlabConnectException("address is not a valid git repository")
    }

    fun getRepositoryWithNamespace(): String {

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
