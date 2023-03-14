package com.example.sportsquiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsquiz.data.SportsQuizRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SportsQuizViewModel(
    private val stateBuilder: StateBuilder,
    private val repository: SportsQuizRepository,
    private val networkHandler: NetworkHandler,
) : ViewModel() {

    val state: MutableStateFlow<SportsQuizState> = MutableStateFlow(SportsQuizState.Loading)

    init {
        networkHandler.listen()
        networkHandler.isNetworkConnected.onEach(::networkUpdate).launchIn(viewModelScope)

        loadConfig()
    }

    private fun networkUpdate(isConnected: Boolean) {
        when (state.value) {
            SportsQuizState.NetworkError -> {
                if (isConnected) {
                    state.update { SportsQuizState.Loading }
                    loadConfig()
                }
            }
            is SportsQuizState.SuccessUrl -> {
                if (!isConnected) {
                    state.update { SportsQuizState.NetworkError }
                }
            }
            else -> Unit
        }
    }

    private fun loadConfig() {
        viewModelScope.launch {
            try {
                val config = repository.getConfig()
                state.value = stateBuilder.build(config)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()

                if (networkHandler.isConnected) {
                    state.value = SportsQuizState.Error
                } else {
                    state.value = SportsQuizState.NetworkError
                }
            }
        }
    }
}