package com.github.idell.gitlabconnect.storage

data class GlobalSettings(
    var enabled: Boolean? = false,
    var tokenConfig: TokenConfiguration? = TokenConfiguration(
        "<insert your connection host here>",
        "<insert your connection token here>"
    )
)

data class TokenConfiguration(var host: String?, var token: String?)
