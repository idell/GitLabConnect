package com.github.idell.gitlabconnect.storage

import java.util.Optional

data class TokenData(val key: String, val token: String)

typealias TokenKey = String

typealias Token = Optional<String>
