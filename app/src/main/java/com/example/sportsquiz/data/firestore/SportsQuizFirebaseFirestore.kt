package com.example.sportsquiz.data.firestore

import com.example.sportsquiz.data.firestore.model.Config
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.MutableSharedFlow
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

/*
* FirebaseRemoteConfig example
*/
class SportsQuizFirebaseRemoteConfig(private val firebaseRemoteConfig: FirebaseRemoteConfig) {

    val url = MutableSharedFlow<String>(replay = 1)

    init {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                url.tryEmit(firebaseRemoteConfig.getString("url"))
            }
        }
    }
}
