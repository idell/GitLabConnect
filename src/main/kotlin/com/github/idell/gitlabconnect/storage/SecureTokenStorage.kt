package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.CredentialStore
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import java.util.Optional

class SecureTokenStorage(private val passwordSafe: CredentialStore = PasswordSafe.instance) {

    fun getToken(key: String): Optional<String> {
        return Optional.ofNullable(passwordSafe.getPassword(createCredentialAttributes(key)))
    }

    fun storeToken(key: String, token: String) {
        val storedToken = passwordSafe.getPassword(createCredentialAttributes(key))
        if (storedToken != null && storedToken != token) {
            passwordSafe.setPassword(createCredentialAttributes(key), token)
        }
    }

    private fun createCredentialAttributes(key: String): CredentialAttributes {
        val serviceName = generateServiceName(SUBSYSTEM, key)
        return CredentialAttributes(serviceName, key)
    }

    companion object {
        private const val SUBSYSTEM = "gitlabconnect"
    }
}
