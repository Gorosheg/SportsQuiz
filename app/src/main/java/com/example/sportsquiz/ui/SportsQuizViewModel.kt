package com.example.sportsquiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsquiz.data.SportsQuizRepository
import com.example.sportsquiz.ui.SportsQuizState.*
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

    val state: MutableStateFlow<SportsQuizState> = MutableStateFlow(Loading)

    init {
        networkHandler.listen()
        networkHandler.isNetworkConnected.onEach(::networkUpdate).launchIn(viewModelScope)

        loadConfig()
    }

    fun onAnswerClick(answerId: Int) {
        val currentState = state.value as SuccessTemplate

        val questionNumber = when (currentState.currentQuestion.id + 1) {
            currentState.questionsList.size -> currentState.currentQuestion.id
            else -> currentState.currentQuestion.id + 1
        }

        state.update {
            SuccessTemplate(
                questionsList = currentState.questionsList,
                currentQuestion = currentState.questionsList[questionNumber],
                usersResult = getUsersResult(currentState, answerId),
                currentQuestionId = currentState.currentQuestionId + 1
            )
        }
    }

    fun retryQuiz() {
        state.update {
            stateBuilder.buildTemplateState()
        }
    }

    private fun getUsersResult(currentState: SuccessTemplate, answerId: Int): Int {
        return if (currentState.currentQuestion.correctAnswerId == answerId) {
            currentState.usersResult + 1
        } else {
            currentState.usersResult
        }
    }

    private fun networkUpdate(isConnected: Boolean) {
        when (state.value) {
            NetworkError -> {
                if (isConnected) {
                    state.update { Loading }
                    loadConfig()
                }
            }

            is SuccessUrl -> {
                if (!isConnected) {
                    state.update { NetworkError }
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
                    state.value = Error
                } else {
                    state.value = NetworkError
                }
            }
        }
    }
}