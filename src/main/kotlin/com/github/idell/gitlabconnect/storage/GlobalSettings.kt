package com.github.idell.gitlabconnect.storage

data class GlobalSettings(
    val enabled: Boolean = false,
    val tokenConfig: TokenConfiguration = TokenConfiguration()
)

data class TokenConfiguration(
    val host: String = "<insert your connection host here>",
    val token: String = "<insert your connection token here>"
)
