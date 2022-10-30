package com.exercise.cardstore.domain.usecase

import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository

class AddCard(
    private val repository: CardRepository
) {
    suspend operator fun invoke(card: Card) {
        if(card.title.isBlank()) {
           // throw Exception
        }
        if(card.firstName.isBlank()) {
           // throw Exception
        }
        repository.insertCard(card)
    }
}