package com.depromeet.team5.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    @Named(NamedKey.BADGE_DOT_FILE)
    fun providePreferencesDataStoreForBadge(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(PreferencesFileKey.BADGE_DOT_FILE) }
    )

    @Provides
    @Singleton
    @Named(NamedKey.LOGIN_TOKEN)
    fun providePreferencesDataStoreForLogin(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(PreferencesFileKey.LOGIN_TOKEN_FILE) }
    )


    object NamedKey {
        const val BADGE_DOT_FILE = "badge_dot_file"
        const val LOGIN_TOKEN = "login_token"
    }

    object PreferencesFileKey {

        const val BADGE_DOT_FILE = "badge_dot_prefs"
        const val LOGIN_TOKEN_FILE = "login_token_file"
    }
}
