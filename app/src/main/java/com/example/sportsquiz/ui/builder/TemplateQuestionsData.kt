package com.example.sportsquiz.ui.builder

import com.example.sportsquiz.ui.model.AnswerItem
import com.example.sportsquiz.ui.model.QuestionItem

fun buildTemplateQuestions(): List<QuestionItem> {
    return listOf(
        QuestionItem(
            id = 0,
            text = "For which team did Michael Jordan spend most of his career playing?",
            answers = buildTemplateAnswers(questionNumber = 0),
            correctAnswerId = 3
        ),
        QuestionItem(
            id = 1,
            text = "In which sport would you have a touchdown?",
            answers = buildTemplateAnswers(questionNumber = 1),
            correctAnswerId = 2
        ),
        QuestionItem(
            id = 2,
            text = "What sport is considered the “king of sports”?",
            answers = buildTemplateAnswers(questionNumber = 2),
            correctAnswerId = 2
        ),
        QuestionItem(
            id = 3,
            text = "How many paddles are used in a kayak?",
            answers = buildTemplateAnswers(questionNumber = 3),
            correctAnswerId = 0
        ),
        QuestionItem(
            id = 4,
            text = "What is the oldest water sport ever recorded?",
            answers = buildTemplateAnswers(questionNumber = 4),
            correctAnswerId = 3
        ),
        QuestionItem(
            id = 5,
            text = "Of all the fighting sports below, which sport wasn’t practised by Bruce Lee?",
            answers = buildTemplateAnswers(questionNumber = 5),
            correctAnswerId = 1
        ),
        QuestionItem(
            id = 6,
            text = "What year did boxing become a legal sport? ",
            answers = buildTemplateAnswers(questionNumber = 6),
            correctAnswerId = 0
        ),
        QuestionItem(
            id = 7,
            text = "How many players are there in a futsal (indoor soccer) team?",
            answers = buildTemplateAnswers(questionNumber = 7),
            correctAnswerId = 2
        ),
        QuestionItem(
            id = 8,
            text = "How many players are there on a baseball team?",
            answers = buildTemplateAnswers(questionNumber = 8),
            correctAnswerId = 3
        ),
        QuestionItem(
            id = 9,
            text = "Which swimming style is not allowed in the Olympics?",
            answers = buildTemplateAnswers(questionNumber = 9),
            correctAnswerId = 1
        )
    )
}

private fun buildTemplateAnswers(questionNumber: Int): List<AnswerItem> {
    return when (questionNumber) {
        0 -> listOf(
            AnswerItem(id = 0, text = "Boston Celtics"),
            AnswerItem(id = 1, text = "Washington Wizards"),
            AnswerItem(id = 2, text = "Los Angeles Lakers"),
            AnswerItem(id = 3, text = "Chicago Bulls")
        )
        1 -> listOf(
            AnswerItem(id = 0, text = "Basketball"),
            AnswerItem(id = 1, text = "Soccer"),
            AnswerItem(id = 2, text = "American football"),
            AnswerItem(id = 3, text = "Baseball")
        )
        2 -> listOf(
            AnswerItem(id = 0, text = "Baseball"),
            AnswerItem(id = 1, text = "Golf"),
            AnswerItem(id = 2, text = "Soccer"),
            AnswerItem(id = 3, text = "Basketball")
        )
        3 -> listOf(
            AnswerItem(id = 0, text = "One"),
            AnswerItem(id = 1, text = "Two"),
            AnswerItem(id = 2, text = "Three"),
            AnswerItem(id = 3, text = "Four")
        )
        4 -> listOf(
            AnswerItem(id = 0, text = "Water polo"),
            AnswerItem(id = 1, text = "Sailing"),
            AnswerItem(id = 2, text = "Canoeing"),
            AnswerItem(id = 3, text = "Diving")
        )
        5 -> listOf(
            AnswerItem(id = 0, text = "Boxing"),
            AnswerItem(id = 1, text = "Wushu"),
            AnswerItem(id = 2, text = "Jeet Kune Do"),
            AnswerItem(id = 3, text = "Fencing")
        )
        6 -> listOf(
            AnswerItem(id = 0, text = "1901"),
            AnswerItem(id = 1, text = "1911"),
            AnswerItem(id = 2, text = "1921"),
            AnswerItem(id = 3, text = "1931")
        )
        7 -> listOf(
            AnswerItem(id = 0, text = "Three"),
            AnswerItem(id = 1, text = "Four"),
            AnswerItem(id = 2, text = "Five"),
            AnswerItem(id = 3, text = "Six")
        )
        8 -> listOf(
            AnswerItem(id = 0, text = "Eight"),
            AnswerItem(id = 1, text = "Eleven"),
            AnswerItem(id = 2, text = "Six"),
            AnswerItem(id = 3, text = "Nine")
        )
        else -> listOf(
            AnswerItem(id = 0, text = "Butterfly"),
            AnswerItem(id = 1, text = "Dog paddle"),
            AnswerItem(id = 2, text = "Freestyle"),
            AnswerItem(id = 3, text = "Backstroke")
        )
    }
}