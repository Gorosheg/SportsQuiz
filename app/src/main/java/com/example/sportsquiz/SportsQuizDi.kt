package com.example.sportsquiz

import com.example.sportsquiz.data.SportsQuizRepository
import com.example.sportsquiz.data.dataStore.SportsQuizDataStore
import com.example.sportsquiz.data.firestore.SportsQuizFirebaseFirestore
import com.example.sportsquiz.ui.NetworkHandler
import com.example.sportsquiz.ui.SportsQuizViewModel
import com.example.sportsquiz.ui.StateBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sportsQuizModule = module {
    viewModel {
        SportsQuizViewModel(get(), get(), get())
    }

    single {
        StateBuilder(get())
    }

    single {
        SportsQuizRepository(get(), get())
    }

    single {
        SportsQuizFirebaseFirestore(get())
    }

    single {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
        }
    }

    single {
        SportsQuizDataStore(androidContext())
    }

    single {
        NetworkHandler(androidContext())
    }
}