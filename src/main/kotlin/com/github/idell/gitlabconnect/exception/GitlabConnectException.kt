package com.github.idell.gitlabconnect.exception

class GitlabConnectException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}
