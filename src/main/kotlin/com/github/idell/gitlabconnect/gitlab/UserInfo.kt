package com.github.idell.gitlabconnect.gitlab

data class UserInfo(val id: Int, val name: String, val state: String) {
    fun isActive(): Boolean = state == "active"
}
