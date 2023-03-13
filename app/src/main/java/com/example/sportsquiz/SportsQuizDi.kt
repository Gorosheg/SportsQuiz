package com.example.sportsquiz

import com.example.sportsquiz.data.Firestore.SportsQuizFirebaseFirestore
import com.example.sportsquiz.ui.SportsQuizViewModel
import com.example.sportsquiz.ui.StateBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sportsQuizModule = module {
    viewModel {
        SportsQuizViewModel(get(), get())
    }

    factory {
        StateBuilder()
    }

    factory {
//        androidContext()
        SportsQuizFirebaseFirestore(get())
    }

    factory<FirebaseFirestore> {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
        }
    }

}