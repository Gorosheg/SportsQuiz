package com.example.sportsquiz.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sportsquiz.R.layout
import com.example.sportsquiz.R.string
import com.example.sportsquiz.databinding.ActivityMainBinding
import com.example.sportsquiz.ui.SportsQuizState.*
import com.example.sportsquiz.ui.recycler.answersDelegate
import com.example.sportsquiz.ui.recycler.base.CommonAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String.format

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: SportsQuizViewModel by viewModel()
    private val webViewClient: SportsQuizWebViewClient by inject()

    private val adapter = CommonAdapter(
        answersDelegate { viewModel.onAnswerClick(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        binding.quiz.answersRecycler.adapter = adapter
        handleWebViewOnBAckPressed()

        viewModel.state.onEach { state -> renderState(state, savedInstanceState) }.launchIn(lifecycleScope)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        binding.webView.webView.saveState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun handleWebViewOnBAckPressed() = with(binding) {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.webView.canGoBack()) {
                    webView.webView.goBack()
                } else Unit
            }
        })
    }

    private fun renderState(state: SportsQuizState, savedInstanceState: Bundle?) = with(binding) {
        progressBar.isVisible = state == Loading
        webView.webView.isVisible = state is SuccessUrl
        quiz.quiz.isVisible = state is SuccessTemplate
        errorMessage.isVisible = state == Error || state == NetworkError

        when (state) {
            Loading -> Unit
            is SuccessUrl -> renderSuccessUrl(state.url, savedInstanceState)
            is SuccessTemplate -> renderSuccessTemplate(state)
            NetworkError -> renderNetworkError()
            Error -> renderError()
        }
    }

    private fun renderSuccessUrl(url: String, savedInstanceState: Bundle?) = with(binding) {
        webView.webView.webViewClient = webViewClient
        webViewClient.setWebViewSettings(webView.webView.settings)

        if (savedInstanceState != null) webView.webView.restoreState(savedInstanceState)
        else webView.webView.loadUrl(url)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderSuccessTemplate(state: SuccessTemplate) = with(binding) {
        adapter.items = state.currentQuestion.answers
        adapter.notifyDataSetChanged()

        state.changeQuestion()
    }

    private fun renderNetworkError() {
        binding.errorMessage.text = getString(string.check_internet_connection)
    }

    private fun renderError() {
        binding.errorMessage.text = getString(string.unknown_error)
    }

    private fun SuccessTemplate.changeQuestion() = with(binding) {
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