package com.github.idell.gitlabconnect.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@State(name = "GitToolBoxProjectSettings", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class GitlabConnectProjectConfigState : PersistentStateComponent<ProjectConfig> {
    private var state: ProjectConfig = ProjectConfig()

    override fun getState(): ProjectConfig {
        synchronized(this) {
            return state
        }
    }

    override fun loadState(state: ProjectConfig) {
        synchronized(this) {
            this.state = state
        }
    }

    companion object {

        @JvmStatic
        fun projectConfig(project: Project): ProjectConfig {
            return getInstance(project).state
        }

        @JvmStatic
        fun getInstance(project: Project): GitlabConnectProjectConfigState {
            return project.service()
        }
    }
}
