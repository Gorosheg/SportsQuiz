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
            SportsQuizState.Loading -> Unit

            is SportsQuizState.SuccessUrl -> {
                webView.webView.isVisible = true
                quiz.quiz.isGone = true
                errorMessage.isGone = true

                showWebView(state.url)
            }
            is SportsQuizState.SuccessTemplate -> {
                webView.webView.isGone = true
                quiz.quiz.isVisible = true
                errorMessage.isGone = true

                adapter.items = state.currentQuestion.answers
                adapter.notifyDataSetChanged()

                if (state.currentQuestionId == state.questionsList.size) {
                    quiz.answersRecycler.isGone = true
                } else {
                    quiz.question.text = state.currentQuestion.text
                    quiz.questionCounter.text =
                        format(
                            resources.getString(string.questions_count),
                            state.currentQuestion.id + 1,
                            state.questionsList.size
                        )
                }
            }
            SportsQuizState.NetworkError -> {
                webView.webView.isGone = true
                quiz.quiz.isGone = true
                errorMessage.isVisible = true

                errorMessage.text = getString(string.check_internet_connection)
            }
            SportsQuizState.Error -> {
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
}