package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

class PasswordStorage(private val hostId: String) {

    fun getToken(): String {
        return PasswordSafe.instance.getPassword(createCredentialAttributes(hostId)) ?: return ""
    }

    fun storeToken(token: String) = PasswordSafe.instance.setPassword(createCredentialAttributes(hostId), token)

    private fun createCredentialAttributes(key: String): CredentialAttributes {
        val serviceName = generateServiceName(SUBSYSTEM, key)
        return CredentialAttributes(serviceName, key)
    }
    //TODO ivn if the key was a fixed string, the storage always retrieve the same couple : hostId-token -> try it!

    companion object {
        private const val SUBSYSTEM = "gitlabconnect"
    }
}
