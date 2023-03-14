package com.example.sportsquiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsquiz.data.SportsQuizRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SportsQuizViewModel(
    private val stateBuilder: StateBuilder,
    private val repository: SportsQuizRepository,
) : ViewModel() {

    val state: MutableStateFlow<SportsQuizState> = MutableStateFlow(SportsQuizState.Loading)

    init {
        viewModelScope.launch {
            try {
                val config = repository.getConfig()
                state.value = stateBuilder.build(config)
            } catch (e: java.lang.Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()

                state.value = SportsQuizState.Error
            }
        }
    }
}