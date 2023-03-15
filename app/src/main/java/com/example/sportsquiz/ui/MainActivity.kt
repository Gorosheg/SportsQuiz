package com.example.sportsquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sportsquiz.R.layout
import com.example.sportsquiz.R.string
import com.example.sportsquiz.databinding.ActivityMainBinding
import com.example.sportsquiz.ui.SportsQuizState.*
import com.example.sportsquiz.ui.recycler.CommonAdapter
import com.example.sportsquiz.ui.recycler.answersDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String.format


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: SportsQuizViewModel by viewModel()

    private val adapter = CommonAdapter(
        answersDelegate { viewModel.onAnswerClick(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        viewModel.state.onEach(::renderState).launchIn(lifecycleScope)

        with(binding) {
            quiz.answersRecycler.adapter = adapter
        }
    }

    private fun renderState(state: SportsQuizState) = with(binding) {
        when (state) {
            Loading -> Unit

            is SuccessUrl -> {
                webView.webView.isVisible = true
                quiz.quiz.isGone = true
                errorMessage.isGone = true

                showWebView(state.url)
            }
            is SuccessTemplate -> {
                webView.webView.isGone = true
                quiz.quiz.isVisible = true
                errorMessage.isGone = true

                adapter.items = state.currentQuestion.answers
                adapter.notifyDataSetChanged()

                state.changeQuestion()
            }
            NetworkError -> {
                webView.webView.isGone = true
                quiz.quiz.isGone = true
                errorMessage.isVisible = true

                errorMessage.text = getString(string.check_internet_connection)
            }
            Error -> {
                webView.webView.isGone = true
                quiz.quiz.isGone = true
                errorMessage.isVisible = true

                errorMessage.text = getString(string.unknown_error)
            }
        }
    }

    private fun showWebView(url: String) {
        with(binding) {
            webView.webView.loadUrl(url)
        }
    }

    private fun SuccessTemplate.changeQuestion() {
        with(binding) {
            if (currentQuestionId == questionsList.size) {
                quiz.answersRecycler.isGone = true
                quiz.question.text = getString(string.game_completed)
                quiz.counterHeader.text = getCounterHeaderText(usersResult)
                quiz.questionCounter.text = buildQuestionCounter(usersResult, questionsList.size)
                quiz.retryImage.isVisible = true
                quiz.retryImage.setOnClickListener { viewModel.retryQuiz() }
            } else {
                quiz.answersRecycler.isVisible = true
                quiz.question.text = currentQuestion.text
                quiz.counterHeader.text = getCounterHeaderText()
                quiz.questionCounter.text = buildQuestionCounter(currentQuestion.id + 1, questionsList.size)
                quiz.retryImage.isGone = true
            }
        }
    }

    private fun getCounterHeaderText(usersResult: Int? = null): String {
        return when (usersResult) {
            0, 1 -> getString(string.try_again)
            2, 3 -> getString(string.bad_result)
            4, 5 -> getString(string.not_bad_result)
            6, 7 -> getString(string.good_result)
            8, 9, 10 -> getString(string.excellent_result)
            else -> getString(string.question_number)
        }
    }

    private fun buildQuestionCounter(firstNum: Int, secondNum: Int) = format(
        getString(string.questions_count),
        firstNum,
        secondNum
    )
}