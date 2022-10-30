package com.exercise.cardstore.data.mapper

import com.exercise.cardstore.data.remote.dto.CardDto
import com.exercise.cardstore.data.local.Card

fun CardDto.toCardEntity(): Card {
    return Card(
        title = title,
        type = type,
        firstName = firstName,
        lastName = lastName,
        cardNumber = cardNumber,
        cardId = cardId,
        referenceNumber = referenceNumber,
        expiryDate = expiryDate,
        color = color,
        timestamp = timestamp
    )
}