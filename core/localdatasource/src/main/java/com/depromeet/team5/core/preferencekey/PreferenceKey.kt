package com.depromeet.team5.core.preferencekey

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey


internal object PreferenceKey {

    val BADGE_CONSUMED_IDS = stringSetPreferencesKey("badge_dot_consumed_retrospection_ids")
    val LOGIN_ACCESS_TOKEN = stringPreferencesKey("login_access_token")
    val LOGIN_REFRESH_TOKEN = stringPreferencesKey("login_refresh_token")


}
