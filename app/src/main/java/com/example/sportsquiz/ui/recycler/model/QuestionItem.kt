package com.example.sportsquiz.ui.recycler.model

class QuestionItem(
    val id: Int,
    val text: String,
    val answers: List<AnswerItem>,
    val correctAnswerId: Int,
)