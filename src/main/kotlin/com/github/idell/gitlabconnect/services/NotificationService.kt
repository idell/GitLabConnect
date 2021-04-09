package com.github.idell.gitlabconnect.services

interface NotificationService {

    fun info(message: String)
    fun warning(message: String)
    fun error(message: String)
}
