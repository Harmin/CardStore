package com.exercise.cardstore.domain.usecase

import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository

class DeleteCard(
    private val repository: CardRepository
) {

    suspend operator fun invoke(card: Card) {
        repository.deleteCard(card)
    }
}