package com.depromeet.team5.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.depromeet.team5.core.di.DataStoreModule
import com.depromeet.team5.core.preferencekey.PreferenceKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
internal class LoginDataStore @Inject constructor(
    @Named(DataStoreModule.NamedKey.LOGIN_TOKEN)
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setAccessToken(token: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.LOGIN_ACCESS_TOKEN] = token
        }[PreferenceKey.LOGIN_ACCESS_TOKEN]
    }

    suspend fun getAccessToken() = dataStore.data
        .map { preferences -> preferences[PreferenceKey.LOGIN_ACCESS_TOKEN] }
        .first()

    suspend fun setRefreshToken(token: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.LOGIN_REFRESH_TOKEN] = token
        }[PreferenceKey.LOGIN_REFRESH_TOKEN]
    }

    suspend fun getRefreshToken() = dataStore.data
        .map { preferences -> preferences[PreferenceKey.LOGIN_REFRESH_TOKEN] }
        .first()
}
