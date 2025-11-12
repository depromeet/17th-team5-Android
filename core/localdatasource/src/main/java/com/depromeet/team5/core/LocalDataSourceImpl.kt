package com.depromeet.team5.core

import androidx.datastore.core.DataStore
import com.depromeet.team5.core.data.datasource.LocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {

    private val CONSUMED_IDS = stringSetPreferencesKey("badge_dot_consumed_retrospection_ids")

    override suspend fun isConsumed(id: Int): Boolean {
        val set = dataStore.data.map { it[CONSUMED_IDS] ?: emptySet() }.first()
        return set.contains(id.toString())
    }

    override suspend fun consume(id: Int): Boolean {
        dataStore.edit { prefs ->
            val cur = prefs[CONSUMED_IDS] ?: emptySet()
            prefs[CONSUMED_IDS] = cur + id.toString()
        }
        return true
    }
}
