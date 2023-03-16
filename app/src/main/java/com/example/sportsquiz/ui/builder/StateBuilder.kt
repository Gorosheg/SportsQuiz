package com.example.sportsquiz.ui.builder

import com.example.sportsquiz.data.firestore.model.Config
import com.example.sportsquiz.ui.NetworkHandler
import com.example.sportsquiz.ui.model.SportsQuizState
import com.example.sportsquiz.ui.model.SportsQuizState.*

class StateBuilder(private val networkHandler: NetworkHandler) {

    fun build(config: Config?): SportsQuizState {
        return if (!networkHandler.isConnected) {
            NetworkError
        } else if (isEmulatorOrGoogle() || config == null || config.url == "") {
            buildTemplateState()
        } else {
            SuccessUrl(url = config.url)
        }
    }

    fun buildTemplateState(): SuccessTemplate {
        return SuccessTemplate(
            questionsList = buildTemplateQuestions(),
            currentQuestion = buildTemplateQuestions().first(),
            usersResult = 0,
            currentQuestionId = 0
        )
    }
}