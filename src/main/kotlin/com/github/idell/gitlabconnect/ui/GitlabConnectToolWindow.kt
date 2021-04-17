package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.gitlab.ProjectInfo
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.SecureTokenStorage
import com.github.idell.gitlabconnect.ui.issue.IssueStubGenerator
import com.github.idell.gitlabconnect.ui.markdown.GitlabRestMarkDownProcessor
import com.github.idell.gitlabconnect.ui.markdown.MarkDownProcessor
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.BrowserHyperlinkListener
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.UIUtil
import com.intellij.util.ui.components.BorderLayoutPanel
import java.awt.FlowLayout
import java.math.BigInteger
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JEditorPane
import javax.swing.JSplitPane
import javax.swing.ListCellRenderer

class GitlabConnectToolWindow(private val toolWindow: ToolWindow, private val issueListener: ListCellRenderer<Issue>) {

    private var rightComponent: JEditorPane = JEditorPane()
    private var scrollable: JBScrollPane = JBScrollPane(rightComponent)
    private var leftComponent: JBPanel<BorderLayoutPanel> = JBPanel(
        VerticalFlowLayout(
            FlowLayout.LEFT,
            DEFAULT_GAP,
            DEFAULT_GAP,
            FILL,
            FILL
        )
    )
    private var externalPanel: JSplitPane = JSplitPane(
        JSplitPane.HORIZONTAL_SPLIT,
        CONTINUOUS_LAYOUT,
        leftComponent,
        scrollable
    )
    private val makDownProcessor: MarkDownProcessor = GitlabRestMarkDownProcessor(
        GitlabConnectGlobalSettings.getInstance(),
        SecureTokenStorage()
    )

    init {
        this.render()
    }

    private fun render() {
        //        val scheduleWithFixedDelay: ScheduledFuture<*> = AppExecutorUtil.getAppScheduledExecutorService()
        //            .scheduleWithFixedDelay(getRunnable(), 5, 5, TimeUnit.MINUTES)
        externalPanel.dividerSize = DEFAULT_DIVIDER_SIZE
        val items = IssueStubGenerator.generate()
        val list: JBList<Issue> = JBList(items)
        list.cellRenderer = issueListener
        leftComponent.add(list)

        list.addListSelectionListener {
            rightComponent.border = createBorder()
            rightComponent.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, USE_DISPLAY_PROPERTIES)
            rightComponent.addHyperlinkListener { BrowserHyperlinkListener() }
            rightComponent.font = UIUtil.getLabelFont()
            rightComponent.isEditable = NOT_EDITABLE
            rightComponent.contentType = PANEL_CONTENT_TYPE
            val html = makDownProcessor.process(
                list.selectedValue,
                // GitlabConnectProjectConfigState
                ProjectInfo(
                    BigInteger.ZERO.toInt(),
                    "rumba",
                    "team-commander"
                )
            )
            rightComponent.text = html
        }
    }

    private fun createBorder() = BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(
            PANEL_SMALL_BORDER,
            PANEL_MEDIUM_BORDER,
            PANEL_SMALL_BORDER,
            PANEL_SMALL_BORDER
        ),
        BorderFactory.createEmptyBorder(
            PANEL_SMALL_BORDER,
            PANEL_SMALL_BORDER,
            PANEL_SMALL_BORDER,
            PANEL_SMALL_BORDER
        )
    )

    fun geContent(): JComponent {
        return externalPanel
    }

    companion object {
        private const val PANEL_CONTENT_TYPE = "text/html"
        private const val PANEL_SMALL_BORDER = 5
        private const val PANEL_MEDIUM_BORDER = 15
        private const val DEFAULT_DIVIDER_SIZE = 1
        private const val DEFAULT_GAP = 0
        private const val NOT_EDITABLE = false
        private const val USE_DISPLAY_PROPERTIES = true
        private const val CONTINUOUS_LAYOUT = true
        private const val FILL = true
    }
}
