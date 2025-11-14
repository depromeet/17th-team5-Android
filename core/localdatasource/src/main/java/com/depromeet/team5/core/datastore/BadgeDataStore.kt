package com.depromeet.team5.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.depromeet.team5.core.di.NamedKey
import com.depromeet.team5.core.preferencekey.PreferenceKey.BADGE_CONSUMED_IDS
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class BadgeDataStore @Inject constructor(
    @Named(NamedKey.BADGE_DOT_FILE)
    private val dataStore: DataStore<Preferences>
) {

    suspend fun isConsumed(id: Int): Boolean {
        val set = dataStore.data.map { it[BADGE_CONSUMED_IDS] ?: emptySet() }.first()
        return set.contains(id.toString())
    }

    suspend fun consume(id: Int): Boolean {
        dataStore.edit { prefs ->
            val cur = prefs[BADGE_CONSUMED_IDS] ?: emptySet()
            prefs[BADGE_CONSUMED_IDS] = cur + id.toString()
        }
        return true
    }
}
