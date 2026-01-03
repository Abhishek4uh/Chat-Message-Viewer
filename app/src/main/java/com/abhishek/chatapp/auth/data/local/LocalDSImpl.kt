package com.abhishek.chatapp.auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDSImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : LocalDS {

    private companion object {
        val AUTH_KEY = booleanPreferencesKey("auth_user_key")
    }

    override suspend fun isLoggedIn(): Boolean {
        return dataStore.data
            .map { prefs -> prefs[AUTH_KEY] ?: false }
            .first()
    }

    override suspend fun setLoggedIn(value: Boolean){
        dataStore.edit { prefs ->
            prefs[AUTH_KEY] = value
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}
