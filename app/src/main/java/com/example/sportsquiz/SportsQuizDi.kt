package com.example.sportsquiz

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.sportsquiz.data.SportsQuizRepository
import com.example.sportsquiz.data.dataStore.SportsQuizDataStore
import com.example.sportsquiz.data.firestore.SportsQuizFirebaseFirestore
import com.example.sportsquiz.ui.LegacyNetworkHandler
import com.example.sportsquiz.ui.NetworkHandler
import com.example.sportsquiz.ui.NougatNetworkHandler
import com.example.sportsquiz.ui.SportsQuizViewModel
import com.example.sportsquiz.ui.builder.StateBuilder
import com.example.sportsquiz.ui.webview.SportsQuizWebViewClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
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
/*
* FirebaseRemoteConfig example
*/
    single<FirebaseRemoteConfig> {
        FirebaseRemoteConfig.getInstance()
    }

    single {
        SportsQuizDataStore(androidContext())
    }

    single<NetworkHandler> {
        val connectivityMonitor =
            if (is24orMore()) NougatNetworkHandler(get())
            else LegacyNetworkHandler(androidContext(), get())

        connectivityMonitor.apply { startListening() }
    }

    single {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single {
        SportsQuizWebViewClient()
    }
}

fun is24orMore(): Boolean {
    return VERSION.SDK_INT >= VERSION_CODES.N
}