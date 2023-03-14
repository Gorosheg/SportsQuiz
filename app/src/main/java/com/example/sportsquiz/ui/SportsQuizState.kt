package com.example.sportsquiz.ui

sealed class SportsQuizState {

    object Loading : SportsQuizState()

    class SuccessUrl(val url: String) : SportsQuizState()

    object SuccessTemplate : SportsQuizState()

    object NetworkError : SportsQuizState()

    object Error : SportsQuizState()
}
