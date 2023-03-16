package com.example.sportsquiz.ui.recycler

import com.example.sportsquiz.databinding.AnswersRecyclerItemsBinding
import com.example.sportsquiz.ui.model.AnswerItem
import com.example.sportsquiz.ui.recycler.base.adapterDelegate

internal fun answersDelegate(onAnswerClick: (id: Int) -> Unit) =
    adapterDelegate<AnswerItem, AnswersRecyclerItemsBinding>(AnswersRecyclerItemsBinding::inflate) {
        answer.setOnClickListener { onAnswerClick.invoke(item.id) }

        bind {
            answerText.text = item.text
        }
    }
