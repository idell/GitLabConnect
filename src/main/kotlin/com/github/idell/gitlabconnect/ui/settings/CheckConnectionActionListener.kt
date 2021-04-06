package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.services.gitlab.GitlabTestService
import com.github.idell.gitlabconnect.storage.TokenConfiguration
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class CheckConnectionActionListener(private val getToken: () -> TokenConfiguration,
                                    private val callback: (switch: Boolean) -> Unit) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) = callback.invoke(GitlabTestService().test(getToken.invoke()))
}
