package com.example.sportsquiz.ui.recycler

import com.example.sportsquiz.databinding.AnswersRecyclerItemsBinding
import com.example.sportsquiz.ui.recycler.model.AnswerItem

internal fun answersDelegate(onAnswerClick: (id: Int) -> Unit) =
    adapterDelegate<AnswerItem, AnswersRecyclerItemsBinding>(AnswersRecyclerItemsBinding::inflate) {
        answer.setOnClickListener { onAnswerClick.invoke(item.id) }

        bind {
            answerText.text = item.text
        }
    }
