package com.example.sportsquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sportsquiz.R
import com.example.sportsquiz.databinding.ActivityMainBinding
import com.example.sportsquiz.ui.recycler.CommonAdapter
import com.example.sportsquiz.ui.recycler.answersDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: SportsQuizViewModel by viewModel()

    private val adapter = CommonAdapter(
        answersDelegate {

        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.onEach(::renderState).launchIn(lifecycleScope)
    }

    private fun renderState(state: SportsQuizState) = with(binding) {
        when (state) {
            SportsQuizState.Loading -> Unit

            is SportsQuizState.SuccessUrl -> {
                noInternetMessage.isGone = true
                webView.webView.isVisible = true
                showWebView(state.url)
            }
            is SportsQuizState.SuccessTemplate -> {
                webView.webView.isGone = true

                adapter.items = state.currentQuestion.answers
                adapter.notifyDataSetChanged()
            }
            SportsQuizState.NetworkError -> {
                noInternetMessage.isVisible = true
                webView.webView.isGone = true
            }
            SportsQuizState.Error -> {
                webView.webView.isGone = true
            }
        }
    }

    private fun showWebView(url: String) {
        with(binding) {
            webView.webView.loadUrl(url)
        }
    }
}