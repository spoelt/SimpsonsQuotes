package com.spoelt.simpsonsquotes.ui.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.spoelt.simpsonsquotes.R
import com.spoelt.simpsonsquotes.databinding.ActivityMainBinding
import com.spoelt.simpsonsquotes.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel

        viewModel.errorMessage.observe(this, Observer {
            displayErrorView(it.isNotEmpty(), it)
        })

        binding.lifecycleOwner = this
    }

    //TODO("Check error handling"
    private fun displayErrorView(isShown: Boolean, message: String? = null) {
        binding.apply {
            textViewErrorMessage.visibility = if (isShown) VISIBLE else GONE
            textViewErrorMessage.text = message
            textViewQuote.visibility = if (isShown) GONE else VISIBLE
            imageViewCharacterImage.visibility = if (isShown) GONE else VISIBLE
            textViewCharacter.visibility = if (isShown) GONE else VISIBLE
        }
    }
}
