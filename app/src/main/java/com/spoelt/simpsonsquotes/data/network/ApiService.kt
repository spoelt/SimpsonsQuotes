package com.spoelt.simpsonsquotes.data.network

import com.spoelt.simpsonsquotes.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("quotes?count=10")
    suspend fun getQuotes(): Response<ArrayList<Quote>>
}