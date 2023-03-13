package com.example.sportsquiz

import com.example.sportsquiz.data.Firestore.SportsQuizFirebaseFirestore
import com.example.sportsquiz.ui.SportsQuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sportsQuizModule = module {
    viewModel {
        SportsQuizViewModel(get())
    }

    factory {
        SportsQuizFirebaseFirestore()
    }
}