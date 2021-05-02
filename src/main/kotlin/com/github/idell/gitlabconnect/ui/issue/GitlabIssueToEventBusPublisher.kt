package com.github.idell.gitlabconnect.ui.issue

import com.intellij.util.messages.MessageBus
import com.intellij.util.messages.Topic
import java.util.*
import kotlin.concurrent.schedule

class GitlabIssueToEventBusPublisher(private val delay: Long,
    private val bus: MessageBus,
    private val issueGenerator: IssueGenerator) {
    fun publish(): Unit {
        Timer().schedule(delay) {
            val publisher: IssueListener = bus.syncPublisher(Topic.create("issues", IssueListener::class.java))

        }
    }
}


class IssueListener {

}
