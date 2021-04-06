package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

class PasswordStorage(private val hostId: String) {

    fun getToken(): String {
        val credentialAttributes = createCredentialAttributes(hostId)
        val password = PasswordSafe.instance.getPassword(credentialAttributes) ?: return ""
        return password!!
    }

    fun storeToken(token: String) {
        val createCredentialAttributes = createCredentialAttributes(hostId)
        PasswordSafe.instance.setPassword(createCredentialAttributes, token)
    }

    private fun createCredentialAttributes(key: String): CredentialAttributes {
        val serviceName = generateServiceName(SUBSYSTEM, key)
        return CredentialAttributes(serviceName, key)
    }

    companion object {
        private const val SUBSYSTEM = "gitlabconnect"
    }
}
