package com.depromeet.team5.core

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.depromeet.team5.core.datastore.LoginDataStore
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class LoginDataStoreTest {

    private val TEST_LOGIN_TOKEN_FILE = "test_login_token_file"

    private lateinit var loginDataStore: LoginDataStore


    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val dataStore = PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(TEST_LOGIN_TOKEN_FILE) }
        )

        loginDataStore = LoginDataStore(dataStore)
    }

    @Test
    fun `access_token_데이터가 없는 경우 null을 리턴한다`() = runTest(UnconfinedTestDispatcher()) {
        val token = loginDataStore.getAccessToken()

        assertEquals(null, token)
    }

    @Test
    fun `access_token을 저장할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val result = loginDataStore.setAccessToken("12345678")

        assertEquals("12345678", result)
    }

    @Test
    fun `refresh_token_데이터가 없는 경우 null을 리턴한다`() = runTest(UnconfinedTestDispatcher()) {
        val token = loginDataStore.getRefreshToken()

        assertEquals(null, token)
    }

    @Test
    fun `refresh_token을 저장할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val result = loginDataStore.setRefreshToken("12345678")

        assertEquals("12345678", result)
    }
}