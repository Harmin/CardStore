package com.exercise.cardstore.data.remote.dto

import com.exercise.cardstore.data.remote.adapter.HexColor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardDto(
    val title: String,
    val type: String,
    val firstName: String,
    val lastName: String,
    val cardNumber: String,
    val cardId: String,
    val referenceNumber: String,
    val expiryDate: String? = "",
    @HexColor val color: Int,
    @Json(ignore = true) val timestamp: Long = -1,
    val id: Int
)