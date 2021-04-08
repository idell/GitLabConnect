package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class CheckConnectionActionListener(
    private val host: String,
    private val callback: (switch: Boolean) -> Unit
) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) {
        val token = SecureTokenStorage().getToken(host).orElse("")
        callback.invoke(GitlabTestService().test(host, token))
    }
}
