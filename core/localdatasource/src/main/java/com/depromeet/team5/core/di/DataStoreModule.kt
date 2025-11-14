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
internal object DataStoreModule {

    private const val BADGE_DOT_FILE = "badge_dot_prefs"
    private const val LOGIN_TOKEN_FILE = "login_token_file"


    @Provides
    @Singleton
    @Named(NamedKey.BADGE_DOT_FILE)
    fun providePreferencesDataStoreForBadge(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(BADGE_DOT_FILE) }
    )

    @Provides
    @Singleton
    @Named(NamedKey.LOGIN_TOKEN)
    fun providePreferencesDataStoreForLogin(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(LOGIN_TOKEN_FILE) }
    )
}