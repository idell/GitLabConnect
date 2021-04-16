package com.github.idell.gitlabconnect.exception

class GitlabProcessException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}