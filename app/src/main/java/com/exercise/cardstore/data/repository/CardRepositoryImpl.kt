package com.exercise.cardstore.data.repository

import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.CardStoreDatabase
import com.exercise.cardstore.data.mapper.toCardEntity
import com.exercise.cardstore.data.remote.CardStoreApi
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val api: CardStoreApi,
    private val db: CardStoreDatabase
): CardRepository {

    private val dao = db.dao

    override fun getCards(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Card>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCardsList = dao.getCards()
            emit(Resource.Success(
                data = localCardsList.map { it }
            ))

            val isDbEmpty = localCardsList.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                api.getCards()
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { card ->
                dao.clearCards()
                dao.insertCards(
                    card.map{
                        it.toCardEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao
                        .getCards()
                        .map { it }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCardById(id: Int): Resource<Card> {
        return try {
            val result = dao.getCardById(id)
            Resource.Success(result)
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load card info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load card info"
            )
        }
    }

    override suspend fun insertCard(card: Card) {
        dao.insertCard(card)
    }

    override suspend fun deleteCard(card: Card) {
        dao.deleteCard(card)
    }
}