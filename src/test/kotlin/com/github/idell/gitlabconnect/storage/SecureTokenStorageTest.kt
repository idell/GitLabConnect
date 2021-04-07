package com.github.idell.gitlabconnect.storage

import com.github.idell.gitlabconnect.gitlab.issues.GitlabConnectDataRetrieverTest.Companion.expecting
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.CredentialStore
import org.assertj.core.api.Assertions.assertThat
import org.jmock.AbstractExpectations.returnValue
import org.jmock.Mockery
import org.jmock.junit5.JUnit5Mockery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class SecureTokenStorageTest {
    var context: Mockery = object : JUnit5Mockery() {}
    private lateinit var secureTokenStorage: SecureTokenStorage
    private lateinit var passwordSafe: CredentialStore

    @BeforeEach
    internal fun setUp() {
        passwordSafe = context.mock(CredentialStore::class.java)
        secureTokenStorage = SecureTokenStorage(passwordSafe)
    }

    @Test
    internal fun `secure storage will return the token`() {
        context.expecting {
            oneOf(passwordSafe).setPassword(CredentialAttributes("IntelliJ Platform gitlabconnect — aKey","aKey"),"aToken")

            allowing(passwordSafe).getPassword(CredentialAttributes("IntelliJ Platform gitlabconnect — aKey","aKey"))
            will(returnValue("aToken"))
        }

        secureTokenStorage.storeToken("aKey", "aToken")

        assertThat(secureTokenStorage.getToken("aKey")).isEqualTo(Optional.ofNullable("aToken"))
    }

    @Test
    internal fun `will return nullable if token not found`() {
        context.expecting {
            oneOf(passwordSafe).setPassword(CredentialAttributes("IntelliJ Platform gitlabconnect — anotherKey","anotherKey"),"aToken")

            allowing(passwordSafe).getPassword(CredentialAttributes("IntelliJ Platform gitlabconnect — aKey","aKey"))
            will(returnValue(null))
        }

        secureTokenStorage.storeToken("anotherKey", "aToken")

        assertThat(secureTokenStorage.getToken("aKey")).isEqualTo(Optional.empty<String>())
    }
}