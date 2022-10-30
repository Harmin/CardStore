package com.exercise.cardstore.domain.repository

import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun getCards(fetchFromRemote: Boolean): Flow<Resource<List<Card>>>

    suspend fun getCardById(id: Int): Resource<Card>

    suspend fun insertCard(card: Card)

    suspend fun deleteCard(card: Card)
}