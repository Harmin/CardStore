package com.exercise.cardstore.data.repository

import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardRepositoryFake: CardRepository {

    private var cardListToReturn = (1..10).map {
        Card(
            id = it,
            type = "type$it",
            title = "title$it",
            firstName = "firstName$it",
            lastName = "lastName$it",
            cardNumber = "12767 37883 33",
            cardId = "333",
            referenceNumber = "3333xcsd",
            expiryDate = "12/27",
            color = it,
            timestamp = -1L
        )
    }

    var cardDetails = Card(
        id = 564,
        type = "any",
        title = "title card",
        firstName = "Harmin",
        lastName = "Arora",
        cardNumber = "12767 37883 33",
        cardId = "333",
        referenceNumber = "3333xcsd",
        expiryDate = "12/27",
        color = 546464,
        timestamp = -1L
    )

    override fun getCards(fetchFromRemote: Boolean): Flow<Resource<List<Card>>> {
        return flow {
            emit(Resource.Success(cardListToReturn))
        }
    }

    override suspend fun getCardById(id: Int): Resource<Card> {
        return Resource.Success(cardDetails)
    }

    override suspend fun insertCard(card: Card) {
    }

    override suspend fun deleteCard(card: Card) {
    }
}