package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.GitApi
import com.github.idell.gitlabconnect.services.TemporaryBalloonNotificationService
import com.github.idell.gitlabconnect.services.configuration.GitlabConnectConfigurationApplicationService.Companion.gitlabConnectConfigurationApplicationService
import com.github.idell.gitlabconnect.storage.GitlabConnectPluginSettings.Companion.hostSettings
import com.github.idell.gitlabconnect.storage.GitlabConnectProjectConfigState.Companion.projectConfig
import com.github.idell.gitlabconnect.storage.GitlabStatus.GITLAB_PROJECT
import com.github.idell.gitlabconnect.storage.GitlabStatus.NOT_ANALYZED
import com.github.idell.gitlabconnect.storage.ProjectConfig
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class GitlabConnectAvailabilityActivity : StartupActivity, DumbAware {

    override fun runActivity(project: Project) {
        if (!gitlabConnectConfigurationApplicationService().isEnabled()) return

        project.basePath
            ?. let {
                GitlabConnectAvailability(GitApi.from("$it/.git"))
                    .generateConfig(hostSettings().host ?: "")
            }
            ?.also { projectConfig(project).update(it) }
            ?.takeIf { projectConfig(project).gitlabStatus == NOT_ANALYZED && it.gitlabStatus == GITLAB_PROJECT }
            ?.apply { project.service<TemporaryBalloonNotificationService>().info("gitlab project found!") }
    }
}

private fun ProjectConfig.update(projectConfig: ProjectConfig) {
    this.gitlabStatus = projectConfig.gitlabStatus
    this.address = projectConfig.address
}
