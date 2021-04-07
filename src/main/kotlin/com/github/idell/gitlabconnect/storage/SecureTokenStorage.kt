package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

class SecureTokenStorage {

    fun getToken(): String {
        return PasswordSafe.instance.getPassword(createCredentialAttributes()) ?: return ""
    }

    fun storeToken(token: String) = PasswordSafe.instance.setPassword(createCredentialAttributes(), token)

    private fun createCredentialAttributes(): CredentialAttributes {
        val serviceName = generateServiceName(SUBSYSTEM, KEY)
        return CredentialAttributes(serviceName, KEY)
    }

    companion object {
        private const val SUBSYSTEM = "gitlabconnect"
        private const val KEY = "gitlab"
    }
}
