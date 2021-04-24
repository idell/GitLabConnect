package com.github.idell.gitlabconnect.storage

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.CredentialStore
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import org.jetbrains.annotations.Nullable
import org.slf4j.LoggerFactory
import java.util.Optional

interface TokenStorage {
    fun getToken(key: TokenKey): Token
    fun storeToken(tokenData: TokenData)
}

class SecureTokenStorage(private val passwordSafe: CredentialStore = PasswordSafe.instance) : TokenStorage {

    override fun getToken(key: TokenKey): Token {
        val ofNullable = Optional.ofNullable(passwordSafe.getPassword(createCredentialAttributes(key)))
        if (key != null && ofNullable.isPresent) {
            LOGGER.info("#debug getting token for key: [$key] result: [${ofNullable.get()}]")
        }
        return ofNullable
    }

    override fun storeToken(tokenData: TokenData) {
        LOGGER.info("#debug saving token: [${tokenData.token}] for key: [${tokenData.key}]")
        val storedToken = passwordSafe.getPassword(createCredentialAttributes(tokenData.key))
        if (storedToken != tokenData.token) {
            passwordSafe.setPassword(createCredentialAttributes(tokenData.key), tokenData.token)
        }
    }

    private fun createCredentialAttributes(key: String): CredentialAttributes {
        val serviceName = generateServiceName(SUBSYSTEM, key)
        return CredentialAttributes(serviceName, key)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SecureTokenStorage::class.java)
        private const val SUBSYSTEM = "gitlabconnect"
    }
}
