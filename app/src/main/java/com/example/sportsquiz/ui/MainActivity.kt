package com.example.sportsquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.sportsquiz.R
import com.example.sportsquiz.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: SportsQuizViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.onEach(::render).launchIn(lifecycleScope)
    }

    private fun render(state: SportsQuizState) = with(binding) {
        when (state) {
            SportsQuizState.Loading -> Unit
            SportsQuizState.Error -> Unit
            is SportsQuizState.SuccessUrl -> showWebView(state.url)
            SportsQuizState.SuccessTemplate -> Unit
        }
    }

    private fun showWebView(url: String) {
        with(binding) {
            webView.webView.settings.javaScriptEnabled = true
            webView.webView.loadUrl(url)
        }
    }
}