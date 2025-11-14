package com.depromeet.team5.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.depromeet.team5.core.data.datasource.LocalDataSource
import com.depromeet.team5.core.datastore.LoginDataStore
import com.depromeet.team5.core.di.NamedKey
import com.depromeet.team5.core.preferencekey.PreferenceKey.BADGE_CONSUMED_IDS
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
internal class LocalDataSourceImpl @Inject constructor(
    @Named(NamedKey.BADGE_DOT_FILE)
    private val dataStore: DataStore<Preferences>,
    private val loginDataStore: LoginDataStore
) : LocalDataSource {


    override suspend fun isConsumed(id: Int): Boolean {
        val set = dataStore.data.map { it[BADGE_CONSUMED_IDS] ?: emptySet() }.first()
        return set.contains(id.toString())
    }

    override suspend fun consume(id: Int): Boolean {
        dataStore.edit { prefs ->
            val cur = prefs[BADGE_CONSUMED_IDS] ?: emptySet()
            prefs[BADGE_CONSUMED_IDS] = cur + id.toString()
        }
        return true
    }

    override suspend fun setAccessToken(token: String) = runCatching {
        loginDataStore.setAccessToken(token)
    }

    override suspend fun getAccessToken(): Result<String?> = runCatching {
        loginDataStore.getAccessToken()
    }

    override suspend fun setRefreshToken(token: String) = runCatching {
        loginDataStore.setRefreshToken(token)
    }

    override suspend fun getRefreshToken(): Result<String?> = runCatching {
        loginDataStore.getRefreshToken()
    }
}
