package com.example.sportsquiz.data.firestore

import com.example.sportsquiz.data.model.Config
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SportsQuizFirebaseFirestore(private val remoteDB: FirebaseFirestore) {

    suspend fun getConfig(): Config? {
        return remoteDB.collection("message")
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.toObject(Config::class.java)
    }
}