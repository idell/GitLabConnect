package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.CredentialStore
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import java.util.Optional

interface TokenStorage {
    fun getToken(key: TokenKey): Token
    fun storeToken(tokenData: TokenData)
}

class SecureTokenStorage(private val passwordSafe: CredentialStore = PasswordSafe.instance) : TokenStorage {

    override fun getToken(key: TokenKey): Token {
        return Optional.ofNullable(passwordSafe.getPassword(createCredentialAttributes(key)))
    }

    override fun storeToken(tokenData: TokenData) {
        val storedToken = passwordSafe.getPassword(createCredentialAttributes(tokenData.key))
        if (storedToken != null && storedToken != tokenData.token) {
            passwordSafe.setPassword(createCredentialAttributes(tokenData.key), tokenData.token)
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
