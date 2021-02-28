package com.spoelt.simpsonsquotes.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spoelt.simpsonsquotes.domain.model.Quote
import com.spoelt.simpsonsquotes.domain.util.getCollection

class QuoteRepositoryImpl : QuoteRepository {

    override val firestore = Firebase.firestore

    override suspend fun getQuotes(): MutableList<Quote> =
        firestore.getCollection(QUOTES).toObjects(Quote::class.java)

    companion object {
        private const val QUOTES = "quotes"
    }
}