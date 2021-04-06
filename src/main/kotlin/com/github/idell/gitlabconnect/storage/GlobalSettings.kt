package com.github.idell.gitlabconnect.storage

data class GlobalSettings(
    var enabled: Boolean? = false,
    var tokenConfig: TokenConfiguration = TokenConfiguration()
)

data class TokenConfiguration(var host: String = "<insert your connection host here>",
                              var token: String = "<insert your connection token here>")