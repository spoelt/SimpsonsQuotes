package com.spoelt.simpsonsquotes.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.spoelt.simpsonsquotes.domain.model.Quote

interface QuoteRepository {

    val firestore: FirebaseFirestore

    suspend fun getQuotes(): MutableList<Quote>
}