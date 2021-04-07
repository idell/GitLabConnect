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
import java.util.Optional

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
            oneOf(passwordSafe).setPassword(CredentialAttributes("$A_SERVICE_NAME — $A_KEY", A_KEY), A_TOKEN)

            allowing(passwordSafe).getPassword(CredentialAttributes("$A_SERVICE_NAME — $A_KEY", A_KEY))
            will(returnValue(A_TOKEN))
        }

        secureTokenStorage.storeToken(A_KEY, A_TOKEN)

        assertThat(secureTokenStorage.getToken(A_KEY)).isEqualTo(Optional.ofNullable(A_TOKEN))
    }

    @Test
    internal fun `will return nullable if token not found`() {
        context.expecting {
            oneOf(passwordSafe).setPassword(CredentialAttributes("$A_SERVICE_NAME — $ANOTHER_KEY", ANOTHER_KEY), A_TOKEN)

            allowing(passwordSafe).getPassword(CredentialAttributes("$A_SERVICE_NAME — $A_KEY", A_KEY))
            will(returnValue(null))
        }

        secureTokenStorage.storeToken(ANOTHER_KEY, A_TOKEN)

        assertThat(secureTokenStorage.getToken(A_KEY)).isEqualTo(Optional.empty<String>())
    }

    companion object {
        private const val A_KEY = "aKey"
        private const val ANOTHER_KEY = "anotherKey"
        private const val A_SERVICE_NAME = "IntelliJ Platform gitlabconnect"
        private const val A_TOKEN = "aToken"
    }
}
