package com.github.idell.gitlabconnect.ui.settings

import com.github.idell.gitlabconnect.GitlabConnectBundle
import com.github.idell.gitlabconnect.ui.settings.GitlabPreferencesComponent.Companion.CONNECTION_LABEL
import com.github.idell.gitlabconnect.ui.settings.GitlabPreferencesComponent.Companion.HOST
import com.github.idell.gitlabconnect.ui.settings.GitlabPreferencesComponent.Companion.TOKEN
import com.intellij.ui.components.JBLabel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.swing.JPanel
import javax.swing.JPasswordField

internal class HostSettingsComponentTest {

    @Test
    internal fun `construct a component with form`() {
        val panel = GitlabPreferencesComponent("aConnectionHost", "aPrivateToken").getPanel()

        val jLabels: List<JBLabel> = panel.components.filterIsInstance<JBLabel>()
        assertThat(jLabels.filter { it.text == GitlabConnectBundle.message(CONNECTION_LABEL, HOST) }).isNotEmpty
        assertThat(jLabels.filter { it.text == GitlabConnectBundle.message(CONNECTION_LABEL, TOKEN) }).isNotEmpty

        val jPanels: List<JPanel> = panel.components.filterIsInstance<JPanel>()
        assertThat(jPanels.size).isEqualTo(3)

        val mutableListOf = mutableListOf<JPasswordField>()
        jPanels.forEach { mutableListOf.addAll(it.components.filterIsInstance<JPasswordField>()) }
        assertThat(mutableListOf).isNotEmpty
    }
}
