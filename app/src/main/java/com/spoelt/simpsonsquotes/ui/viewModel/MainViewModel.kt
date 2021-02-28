package com.spoelt.simpsonsquotes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.simpsonsquotes.domain.model.Quote
import com.spoelt.simpsonsquotes.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: QuoteRepository) : ViewModel() {
    private var _quotes: MutableList<Quote> = mutableListOf()
    private var index: Int = STARTING_INDEX

    private val _currentQuote: MutableLiveData<Quote> = MutableLiveData()
    val currentQuote: LiveData<Quote>
        get() = _currentQuote

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            _quotes = repository.getQuotes()

            //TODO("Check if still working correctly when a lot of data is loaded. Add delay in repo")
            _currentQuote.value = _quotes[STARTING_INDEX]
        }
    }

    fun getNextQuote() {
        index++
        _currentQuote.value = if (index == _quotes.size) {
            index = STARTING_INDEX
            _quotes[STARTING_INDEX]
        } else _quotes[index]
    }

    //TODO("Check")
    private fun handleError(message: String) {
        _errorMessage.value = message
    }

    companion object {
        const val STARTING_INDEX = 0
    }
}