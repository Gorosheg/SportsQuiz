package com.example.sportsquiz.data

import com.example.sportsquiz.data.dataStore.SportsQuizDataStore
import com.example.sportsquiz.data.firestore.SportsQuizFirebaseFirestore
import com.example.sportsquiz.data.firestore.model.Config

class SportsQuizRepository(
    private val firebaseFirestore: SportsQuizFirebaseFirestore,
    private val dataStore: SportsQuizDataStore,
) {

    suspend fun getConfig(): Config? {
        val savedUrl = getSavedUrl()

        return if (savedUrl.isNullOrBlank()) {
            getAndSaveNewConfig()
        } else {
            Config(url = savedUrl)
        }
    }

    private suspend fun getSavedUrl(): String? {
        return dataStore.getUrl()
    }

    private suspend fun getAndSaveNewConfig(): Config? {
        val config = firebaseFirestore.getConfig()
        saveUrl(config?.url ?: "")
        return config
    }

    private suspend fun saveUrl(url: String) {
        dataStore.saveUrl(url)
    }
}