package com.exercise.cardstore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    val title: String,
    val type: String,
    val firstName: String,
    val lastName: String,
    val cardNumber: String,
    val cardId: String,
    val referenceNumber: String,
    val expiryDate: String?,
    val color: Int,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)
