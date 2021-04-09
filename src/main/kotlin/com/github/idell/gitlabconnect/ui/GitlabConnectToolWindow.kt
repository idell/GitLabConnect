package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.Issue
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.UIUtil
import com.intellij.util.ui.components.BorderLayoutPanel
import org.apache.commons.io.FileUtils
import java.awt.Color
import java.awt.Component
import java.io.File
import java.nio.charset.Charset
import javax.swing.JComponent
import javax.swing.JEditorPane
import javax.swing.JList
import javax.swing.JSplitPane
import javax.swing.ListCellRenderer
import kotlin.random.Random

class GitlabConnectToolWindow(val toolWindow: ToolWindow) {

    private var leftComponent: JBPanel<BorderLayoutPanel> = JBPanel()
    private var rightComponent: JEditorPane = JEditorPane()
    private var scrollable : JBScrollPane = JBScrollPane(rightComponent)
    private var externalPanel: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftComponent, scrollable)

    init {
        this.render()
    }

    private fun render() {

        val items = ShowIssueListener.IssueStubGenerator.generate()
        val list: JBList<Issue> = JBList(items)
        list.cellRenderer = ShowIssueListener()
        leftComponent.add(list)
        rightComponent.isEditable = false
        list.addListSelectionListener {
            rightComponent.contentType="text/markdown"

//            rightComponent.editorKit=JBHtmlEditorKit()
            rightComponent.text = list.selectedValue.description
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
        if (isSelected) {
            jbTextField.background = UIUtil.getFocusedFillColor()
        }

        return jbTextField

    }

    object IssueStubGenerator {
        fun generate(): MutableList<Issue> =
            mutableListOf(Issue("Quisque eget condimentum erat. Interdum et malesuada.",
                                "http://www.example.com/agreement/attack.html",
                                listOf("great vengeance", "darkness"), "aDescription"),
                          Issue("Morbi tincidunt tempor rutrum. Donec accumsan odio.",
                                "http://airport.example.com/account.php",
                                listOf("weirdo techie"),
                                FileUtils.readFileToString(File("/Users/idelloro/Projects/GitLabConnect/src/main/kotlin/com/github/idell/gitlabconnect/ui/settings/description.txt"),Charset.forName("UTF-8"))),
                          Issue("Nulla facilisi. Sed in lorem cursus, vehicula.",
                                "http://bubble.example.com/?box=authority",
                                listOf("evil men"), "aDescription"))
    }

    object RandomColorGenerator {
        fun generateColor(): Color {
            return Color(Random.nextInt(0, 256),
                         Random.nextInt(0, 256),
                         Random.nextInt(0, 256))
        }
    }
}
