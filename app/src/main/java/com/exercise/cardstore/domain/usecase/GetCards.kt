package com.exercise.cardstore.domain.usecase

import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetCards(
    private val repository: CardRepository
) {

    operator fun invoke(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Card>>> {
        return repository.getCards(fetchFromRemote)
    }
}