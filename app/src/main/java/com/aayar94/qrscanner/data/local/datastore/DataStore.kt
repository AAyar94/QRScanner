package com.aayar94.qrscanner.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.aayar94.qrscanner.core.Constants.Companion.IS_BEEP_ENABLED
import com.aayar94.qrscanner.core.Constants.Companion.IS_ONBOARDING_FINISHED
import com.aayar94.qrscanner.core.Constants.Companion.IS_VIBRATE_ENABLED
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
        val isVibrateEnabled = booleanPreferencesKey(IS_VIBRATE_ENABLED)
        val isBeepEnabled = booleanPreferencesKey(IS_BEEP_ENABLED)
    }

    private val safeData = dataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }

    val isOnboardingFinished = safeData.map { it[PreferenceKeys.isOnboardingFinished] ?: false }

    val isVibrateEnabled = safeData.map { it[PreferenceKeys.isVibrateEnabled] ?: true }

    val isBeepEnabled = safeData.map { it[PreferenceKeys.isBeepEnabled] ?: true }

    suspend fun saveOnboardingState(isFinished: Boolean) {
        dataStore.edit { it[PreferenceKeys.isOnboardingFinished] = isFinished }
    }

    suspend fun saveVibrateState(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.isVibrateEnabled] = enabled }
    }

    suspend fun saveBeepState(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.isBeepEnabled] = enabled }
    }

}