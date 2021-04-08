package com.github.idell.gitlabconnect.activities

import com.github.idell.gitlabconnect.git.GitApi
import com.github.idell.gitlabconnect.services.TemporaryBalloonNotificationService
import com.github.idell.gitlabconnect.storage.GitlabConnectGlobalSettings
import com.github.idell.gitlabconnect.storage.GitlabConnectProjectConfigState.Companion.actualConfig
import com.github.idell.gitlabconnect.storage.GitlabStatus.GITLAB_PROJECT
import com.github.idell.gitlabconnect.storage.GitlabStatus.NOT_ANALYZED
import com.github.idell.gitlabconnect.storage.ProjectConfig
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class GitlabConnectAvailabilityActivity : StartupActivity, DumbAware {

    override fun runActivity(project: Project) {
        val globalSettings = GitlabConnectGlobalSettings.get()
        val previousStatus = actualConfig(project).gitlabStatus
        val notificationService: TemporaryBalloonNotificationService = project.service()

        if (!globalSettings.enabled) return

        project.basePath
            ?. let {
                GitlabConnectAvailability(GitApi.from("$it/.git"))
                    .generateConfig(globalSettings.tokenConfig.host)
            }
            ?.also { actualConfig(project).update(it) }
            ?.takeIf { previousStatus == NOT_ANALYZED && it.gitlabStatus == GITLAB_PROJECT }
            ?.apply { notificationService.info("gitlab project found!") }
    }
}

private fun ProjectConfig.update(projectConfig: ProjectConfig) {
    this.gitlabStatus = projectConfig.gitlabStatus
    this.address = projectConfig.address
}
