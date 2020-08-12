package com.spoelt.simpsonsquotes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.simpsonsquotes.data.model.Quote
import com.spoelt.simpsonsquotes.data.network.ApiClient
import com.spoelt.simpsonsquotes.data.network.ApiService
import com.spoelt.simpsonsquotes.data.states.NetworkState
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    private val apiService = ApiClient().getClient().create(ApiService::class.java)

    val _quoteList: MutableLiveData<MutableList<Quote>> = MutableLiveData()
    val quoteList: LiveData<MutableList<Quote>>
        get() = _quoteList

    val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            val quotesResult = fetchQuotes()
            handleResult(quotesResult)
        }
    }

    private suspend fun fetchQuotes(): NetworkState {
        return try {
            val response = apiService.getQuotes()
            if (response.isSuccessful) {
                NetworkState.Success(response.body()!!)
            } else {
                when (response.code()) {
                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error(response.message())
                }
            }
        } catch (error: IOException) {
            NetworkState.NetworkException(error.message!!)
        }
    }

    private fun handleResult(networkState: NetworkState) {
        return when (networkState) {
            is NetworkState.Success -> fillListWithQuotes(networkState.data)
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(networkState.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(networkState.exception)
            is NetworkState.HttpErrors.BadGateWay -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(networkState.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(networkState.exception)
            is NetworkState.Error -> handleError(networkState.error)
            is NetworkState.NetworkException -> handleError(networkState.error)
        }
    }

    private fun fillListWithQuotes(data: ArrayList<Quote>) {
        _quoteList.value = data
        _errorMessage.value = ""
    }

    private fun handleError(message: String) {
        _errorMessage.value = message
    }

    @ExperimentalStdlibApi
    fun getNextQuote() {
        val list = _quoteList.value

        if (list.isNullOrEmpty()) {
            getQuotes()
            return
        }

        list.removeFirst()

        if (list.isEmpty()) {
            getQuotes()
        } else {
            _quoteList.value = list
        }
    }
}