package com.github.idell.gitlabconnect.ui.issue.listener

import com.github.idell.gitlabconnect.gitlab.Issue
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.UIUtil
import java.awt.Component
import java.awt.Dimension
import javax.swing.JList
import javax.swing.ListCellRenderer

class ShowIssueListener : ListCellRenderer<Issue> {

    override fun getListCellRendererComponent(
        list: JList<out Issue>,
        value: Issue,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val field = JBTextField(value.title)
        field.font = UIUtil.getTreeFont()
        field.border = JBEmptyBorder(EMPTY_BORDER_OFFSET)
        field.preferredSize = Dimension(value.title.length * DOUBLE, DEFAULT_FIELD_HEIGHT)
        if (isSelected) {
            field.background = UIUtil.getListSelectionBackground(FOCUSED)
        }
        return field
    }

    companion object {
        private const val DOUBLE = 2
        private const val DEFAULT_FIELD_HEIGHT = 35
        private const val FOCUSED = true
        private const val EMPTY_BORDER_OFFSET = 0
    }
}
