package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferenceDataStore {
    private val Context.dataStore by preferencesDataStore(name = "user_ds")

    fun getString(context: Context, key: String) : Flow<String?> {
        val dataStoreKey = stringPreferencesKey(key)
        return context.dataStore.data.map {preferences ->
            preferences[dataStoreKey]
        }
    }

    suspend fun setString(context: Context, key: String,value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }
}