package com.example.sportsquiz.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SportsQuizDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sports_quiz")
    private val url = stringPreferencesKey("url")

    suspend fun getUrl(): String? {
        return context.dataStore.data.first()[url]
    }

    suspend fun saveUrl(url: String) {
        context.dataStore.edit { prefs ->
            prefs[this.url] = url
        }
    }
}