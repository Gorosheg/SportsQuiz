package com.example.sportsquiz.data.Firestore

import com.example.sportsquiz.data.model.Config
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.tasks.await

class SportsQuizFirebaseFirestore {

    private val remoteDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
    }

    suspend fun getConfig(): Config? {
      return remoteDB.collection("message")
            .get()
            .await()
             .documents
             .firstOrNull()
             ?.toObject(Config::class.java)
    }
}