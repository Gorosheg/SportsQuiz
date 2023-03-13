package com.example.sportsquiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsquiz.data.Firestore.SportsQuizFirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

class SportsQuizViewModel(
    private val firebaseFirestore: SportsQuizFirebaseFirestore,
) : ViewModel() {

    val state: MutableStateFlow<SportsQuizState> = MutableStateFlow(SportsQuizState.Loading)

    init {
        viewModelScope.launch {
            try {
                val config = firebaseFirestore.getConfig()
                state.value = SportsQuizState.SuccessUrl(config?.url!!)
            } catch (e: java.lang.Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()

                state.value = SportsQuizState.Error
            }
        }
    }
}