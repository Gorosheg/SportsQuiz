package com.example.sportsquiz.ui

import android.os.Build
import com.example.sportsquiz.BuildConfig
import com.example.sportsquiz.data.firestore.model.Config
import com.example.sportsquiz.ui.SportsQuizState.*
import com.example.sportsquiz.ui.recycler.model.AnswerItem
import com.example.sportsquiz.ui.recycler.model.QuestionItem
import java.util.*

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

    private fun isEmulatorOrGoogle(): Boolean {
        if (BuildConfig.DEBUG) return false

        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND

        return (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware.equals("goldfish")
                || brand.contains("google")
                || buildHardware.equals("vbox86")
                || buildProduct.equals("sdk")
                || buildProduct.equals("google_sdk")
                || buildProduct.equals("sdk_x86")
                || buildProduct.equals("vbox86p")
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
                || (brand.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk" == buildProduct
    }

    private fun buildTemplateQuestions(): List<QuestionItem> {
        return listOf(
            QuestionItem(
                0,
                "For which team did Michael Jordan spend most of his career playing?",
                buildTemplateAnswers(0),
                3
            ),
            QuestionItem(
                1,
                "In which sport would you have a touchdown?",
                buildTemplateAnswers(1),
                2
            ),
            QuestionItem(
                2,
                "What sport is considered the “king of sports”?",
                buildTemplateAnswers(2),
                2
            ),
            QuestionItem(
                3,
                "How many paddles are used in a kayak?",
                buildTemplateAnswers(3),
                0
            ),
            QuestionItem(
                4,
                "What is the oldest water sport ever recorded?",
                buildTemplateAnswers(4),
                3
            ),
            QuestionItem(
                5,
                "Of all the fighting sports below, which sport wasn’t practised by Bruce Lee?",
                buildTemplateAnswers(5),
                1
            ),
            QuestionItem(
                6,
                "What year did boxing become a legal sport? ",
                buildTemplateAnswers(6),
                0
            ),
            QuestionItem(
                7,
                "How many players are there in a futsal (indoor soccer) team?",
                buildTemplateAnswers(7),
                2
            ),
            QuestionItem(
                8,
                "How many players are there on a baseball team?",
                buildTemplateAnswers(8),
                3
            ),
            QuestionItem(
                9,
                "Which swimming style is not allowed in the Olympics?",
                buildTemplateAnswers(9),
                1
            )
        )
    }

    private fun buildTemplateAnswers(questionNumber: Int): List<AnswerItem> {
        return when (questionNumber) {
            0 -> listOf(
                AnswerItem(0, "Boston Celtics"),
                AnswerItem(1, "Washington Wizards"),
                AnswerItem(2, "Los Angeles Lakers"),
                AnswerItem(3, "Chicago Bulls")
            )
            1 -> listOf(
                AnswerItem(0, "Basketball"),
                AnswerItem(1, "Soccer"),
                AnswerItem(2, "American football"),
                AnswerItem(3, "Baseball")
            )
            2 -> listOf(
                AnswerItem(0, "Baseball"),
                AnswerItem(1, "Golf"),
                AnswerItem(2, "Soccer"),
                AnswerItem(3, "Basketball")
            )
            3 -> listOf(
                AnswerItem(0, "One"),
                AnswerItem(1, "Two"),
                AnswerItem(2, "Three"),
                AnswerItem(3, "Four")
            )
            4 -> listOf(
                AnswerItem(0, "Water polo"),
                AnswerItem(1, "Sailing"),
                AnswerItem(2, "Canoeing"),
                AnswerItem(3, "Diving")
            )
            5 -> listOf(
                AnswerItem(0, "Boxing"),
                AnswerItem(1, "Wushu"),
                AnswerItem(2, "Jeet Kune Do"),
                AnswerItem(3, "Fencing")
            )
            6 -> listOf(
                AnswerItem(0, "1901"),
                AnswerItem(1, "1911"),
                AnswerItem(2, "1921"),
                AnswerItem(3, "1931")
            )
            7 -> listOf(
                AnswerItem(0, "Three"),
                AnswerItem(1, "Four"),
                AnswerItem(2, "Five"),
                AnswerItem(3, "Six")
            )
            8 -> listOf(
                AnswerItem(0, "Eight"),
                AnswerItem(1, "Eleven"),
                AnswerItem(2, "Six"),
                AnswerItem(3, "Nine")
            )
            else -> listOf(
                AnswerItem(0, "Butterfly"),
                AnswerItem(1, "Dog paddle"),
                AnswerItem(2, "Freestyle"),
                AnswerItem(3, "Backstroke")
            )
        }
    }
}