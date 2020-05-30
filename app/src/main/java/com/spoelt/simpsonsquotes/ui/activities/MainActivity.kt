package com.spoelt.simpsonsquotes.ui.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.spoelt.simpsonsquotes.R
import com.spoelt.simpsonsquotes.data.model.Quote
import com.spoelt.simpsonsquotes.databinding.ActivityMainBinding
import com.spoelt.simpsonsquotes.ui.viewModel.MainViewModel
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.quoteList.observe(this, Observer {
            it?.let {
                displayCurrentQuote(it)
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    showErrorView(it)
                } else {
                    hideErrorView()
                }
            }
        })

        binding.lifecycleOwner = this
    }

    private fun displayCurrentQuote(list: MutableList<Quote>) {
        val quoteToDisplay = list.first()

        binding.textViewCharacter.text = quoteToDisplay.character
        binding.textViewQuote.text = quoteToDisplay.quote

        Picasso.get()
            .load(quoteToDisplay.imageUrl)
            // if the image is not delivered from memory, picasso looks at disk cache - skip that!
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .error(R.drawable.ic_no_image)
            .into(binding.imageViewCharacterImage)
    }

    private fun showErrorView(message: String) {
        binding.textViewErrorMessage.visibility = VISIBLE
        binding.textViewErrorMessage.text = message
        binding.textViewQuote.visibility = GONE
        binding.imageViewCharacterImage.visibility = GONE
        binding.textViewCharacter.visibility = GONE
    }

    private fun hideErrorView() {
        binding.textViewErrorMessage.visibility = GONE
        binding.textViewQuote.visibility = VISIBLE
        binding.imageViewCharacterImage.visibility = VISIBLE
        binding.textViewCharacter.visibility = VISIBLE
    }
}
