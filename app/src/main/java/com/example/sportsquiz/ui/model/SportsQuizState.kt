package com.example.sportsquiz.ui.model

sealed class SportsQuizState {

    object Loading : SportsQuizState()

    class SuccessUrl(val url: String) : SportsQuizState()

    class SuccessTemplate(
        val questionsList: List<QuestionItem>,
        val currentQuestion: QuestionItem,
        val usersResult: Int,
        val currentQuestionId: Int,
    ) : SportsQuizState()

    object NetworkError : SportsQuizState()

    object Error : SportsQuizState()
}
