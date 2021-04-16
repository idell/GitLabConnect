// package com.github.idell.gitlabconnect.ui.issue
//
// import com.github.idell.gitlabconnect.gitlab.Issue
// import com.intellij.util.ui.UIUtil
// import java.awt.Component
// import javax.swing.JList
// import javax.swing.JTextArea
// import javax.swing.ListCellRenderer
//
// class ShowIssueListener : ListCellRenderer<Issue> {
//    override fun getListCellRendererComponent(list: JList<out Issue>,
//        value: Issue,
//        index: Int,
//        isSelected: Boolean,
//        cellHasFocus: Boolean): Component {
//
//        return jTextArea(value, isSelected).invoke()
//
//    }
//
//    private fun jTextArea(value: Issue,
//        isSelected: Boolean): ()->JTextArea {
//        val jbTextArea = JTextArea(value.title)
//        jbTextArea.text = value.title
//        jbTextArea.lineWrap = true
//        jbTextArea.wrapStyleWord = true
//        jbTextArea.rows = jbTextArea.lineCount
//        if (isSelected) {
//            jbTextArea.background = UIUtil.getFocusedFillColor()
//        }
//        return { jbTextArea }
//    }
//
// }
