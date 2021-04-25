package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.gitlab.GitlabTokenConfiguration
import com.github.idell.gitlabconnect.services.gitlab.GitlabTestConnectionService.Companion.gitlabTestConnectionService
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class CheckConnectionActionListener(
    private val getToken: () -> GitlabTokenConfiguration,
    private val callback: (switch: Boolean) -> Unit
) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) = callback.invoke(
        gitlabTestConnectionService().test(getToken.invoke())
    )
}
