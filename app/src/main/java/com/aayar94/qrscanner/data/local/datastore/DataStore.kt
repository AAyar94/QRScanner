package com.aayar94.qrscanner.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.aayar94.qrscanner.core.Constants.Companion.IS_ONBOARDING_FINISHED
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val dataStoreName = "QRDataStore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = dataStoreName)

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    private object PreferenceKeys {
        val isOnboardingFinished = booleanPreferencesKey(IS_ONBOARDING_FINISHED)
    }

    val isOnboardingFinished = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val isFinishedString = preferences[PreferenceKeys.isOnboardingFinished]
        isFinishedString ?: false
    }

    suspend fun saveOnboardingState(isFinished: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.isOnboardingFinished] = isFinished
        }
    }

}