package com.spoelt.simpsonsquotes.domain.util

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun FirebaseFirestore.getCollection(collectionName: String): QuerySnapshot {
    return suspendCancellableCoroutine { continuation ->
        this.collection(collectionName).get()
            .addOnSuccessListener { querySnapshot ->
                continuation.resume(querySnapshot)
            }
            .addOnFailureListener { failure ->
                continuation.resumeWithException(failure)
            }
    }
}