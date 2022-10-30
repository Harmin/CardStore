package com.exercise.cardstore.domain.usecase

import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository

class GetCard(
    private val repository: CardRepository
) {

    suspend operator fun invoke(id: Int): Resource<Card> {
        return repository.getCardById(id)
    }
}