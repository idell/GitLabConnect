package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.Issue
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.UIUtil
import com.intellij.util.ui.components.BorderLayoutPanel
import java.awt.Color
import java.awt.Component
import javax.swing.JComponent
import javax.swing.JList
import javax.swing.JSplitPane
import javax.swing.ListCellRenderer
import kotlin.random.Random

class GitlabConnectToolWindow(val toolWindow: ToolWindow) {

    private var leftComponent: JBPanel<BorderLayoutPanel> = JBPanel()
    private var rightComponent : JBTextField = JBTextField("stocazzo")
    private var externalPanel: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftComponent, rightComponent)

    init {
        this.render()
    }

    private fun render() {
        val items = mutableListOf(Issue("Quisque eget condimentum erat. Interdum et malesuada.",
                                 "http://www.example.com/agreement/attack.html",
                                 listOf("great vengeance", "darkness")),
                           Issue("Morbi tincidunt tempor rutrum. Donec accumsan odio.",
                                 "http://airport.example.com/account.php",
                                 listOf("weirdo techie")),
                           Issue("Nulla facilisi. Sed in lorem cursus, vehicula.",
                                 "http://bubble.example.com/?box=authority",
                                 listOf("evil men")))
        val list: JBList<Issue> = JBList(items)
        list.cellRenderer = ShowIssueListener()
        leftComponent.add(list)
        rightComponent.isEditable = false
        list.addListSelectionListener {
            rightComponent.text=list.selectedValue.link
        }
        externalPanel.dividerSize = 1
    }

    fun geContent(): JComponent {
        return externalPanel
    }
}

class ShowIssueListener : ListCellRenderer<Issue> {
    override fun getListCellRendererComponent(list: JList<out Issue>?,
        value: Issue,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean): Component {
        //TODO ivn check jbTextField is not editable! -> set not editable
        val jbTextField = JBTextField("${value.title}")
        if (isSelected){
            jbTextField.background= UIUtil.getFocusedFillColor()
        }

        return jbTextField

    }

    object RandomColorGenerator{
        fun generateColor():Color{
            return Color(Random.nextInt(0, 256),
                         Random.nextInt(0, 256),
                         Random.nextInt(0, 256))
        }
    }
}
