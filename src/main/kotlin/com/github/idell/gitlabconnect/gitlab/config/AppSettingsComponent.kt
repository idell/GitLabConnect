package com.github.idell.gitlabconnect.gitlab.config

import javax.swing.JPanel
import com.intellij.ui.components.JBLabel

import com.intellij.util.ui.FormBuilder
import com.intellij.ui.components.JBTextField


class GitlabConnectSettingsComponent {
    private var myMainPanel: JPanel
    private val myUserNameText = JBTextField()
    private val myTokenText = JBTextField()

    init {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("Enter user name: "), myUserNameText, 1, false)
                .addLabeledComponent(JBLabel("Enter gitlab connection token"), myTokenText, 1)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    fun getPanel(): JPanel {
        return myMainPanel
    }

    fun getMyUserNameText() = myUserNameText

    fun getMyTokenText() = myTokenText


}