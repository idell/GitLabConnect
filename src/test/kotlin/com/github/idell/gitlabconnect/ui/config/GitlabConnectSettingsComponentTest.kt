package com.github.idell.gitlabconnect.ui.config

import com.intellij.ui.components.JBLabel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.swing.JPanel
import javax.swing.JPasswordField

internal class GitlabConnectSettingsComponentTest {

    @Test
    internal fun `construct a component with form`() {
        val panel = GitlabConnectSettingsComponent().getPanel()

        val jLabels: List<JBLabel> = panel.components.filterIsInstance<JBLabel>()
        assertThat(jLabels.filter { it.text == "Gitlab host: " }).isNotEmpty
        assertThat(jLabels.filter { it.text == "Gitlab connection token:" }).isNotEmpty

        val jPanels: List<JPanel> = panel.components.filterIsInstance<JPanel>()
        assertThat(jPanels.size).isEqualTo(2)

        val jPasswordFields: List<JPasswordField> = panel.components.filterIsInstance<JPasswordField>()
        assertThat(jPasswordFields).isNotEmpty
    }
}
