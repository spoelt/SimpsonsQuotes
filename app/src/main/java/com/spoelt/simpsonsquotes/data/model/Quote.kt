package com.spoelt.simpsonsquotes.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    val quote: String,
    val character: String,
    @SerializedName("image") val imageUrl: String
)