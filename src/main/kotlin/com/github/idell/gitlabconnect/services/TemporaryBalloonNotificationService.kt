package com.github.idell.gitlabconnect.services

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project


@Service
class TemporaryBalloonNotificationService(private val project: Project) : NotificationService {

    private val notificationGroup = NotificationGroup("gitlabConnect",
        NotificationDisplayType.BALLOON, true)

    override fun info(message: String) {
        notificationGroup.createNotification(message, NotificationType.INFORMATION)
            .notify(project)
    }

    override fun warning(message: String) {
        notificationGroup.createNotification(message, NotificationType.WARNING)
            .notify(project)
    }

    override fun error(message: String) {
        notificationGroup.createNotification(message, NotificationType.ERROR)
            .notify(project)
    }
}