package com.github.idell.gitlabconnect.ui

import com.intellij.openapi.wm.ToolWindow
import java.awt.FlowLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class GitlabConnectToolWindow(val toolWindow: ToolWindow) {

    private var myComponent: JPanel? = JPanel()
    private var myComponent2: JLabel? = JLabel()


    init {
        this.render()
    }

    private fun render() {
        myComponent?.layout = FlowLayout()
        myComponent2?.text = "My fantabolulus text"
        myComponent?.add(myComponent2)
    }

    fun geContent(): JComponent? {
        return myComponent

    }

}
