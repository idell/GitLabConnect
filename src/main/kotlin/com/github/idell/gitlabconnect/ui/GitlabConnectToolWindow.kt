package com.github.idell.gitlabconnect.ui

import com.github.idell.gitlabconnect.gitlab.Issue
import com.github.idell.gitlabconnect.services.gitlab.GitlabConnectApiService
import com.github.idell.gitlabconnect.ui.issue.IssueStubGenerator
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ResourceUtil
import com.intellij.util.ui.UIUtil
import com.intellij.util.ui.components.BorderLayoutPanel
import java.awt.Desktop
import java.awt.FlowLayout
import java.net.URL
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JEditorPane
import javax.swing.JSplitPane
import javax.swing.ListCellRenderer
import javax.swing.text.html.HTMLEditorKit

class GitlabConnectToolWindow(
    private val toolWindow: ToolWindow,
    private val issueListener: ListCellRenderer<Issue>,
    private val project: Project
) {

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
        rightComponent.addHyperlinkListener(BrowserHyperLinkListener(Desktop.getDesktop()))
        rightComponent.isEditable = NOT_EDITABLE
        rightComponent.contentType = PANEL_CONTENT_TYPE
        rightComponent.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, USE_DISPLAY_PROPERTIES)
        rightComponent.border = createBorder()
        useDifferentCSSForNotDarculaTheme()
        list.addListSelectionListener {
            rightComponent.text = project.service<GitlabConnectApiService>().getApi().markdownApi(list.selectedValue)
        }
    }

    private fun useDifferentCSSForNotDarculaTheme() {
        if (!UIUtil.isUnderDarcula()) {
            val resource: URL = ResourceUtil.getResource(
                this::class.java,
                CSS_FOLDER,
                CSS_FILE
            )
            HTMLEditorKit().styleSheet.importStyleSheet(resource.toURI().toURL())
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
        private const val CSS_FOLDER = "/css/"
        private const val CSS_FILE = "whiteTheme.css"
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
