package com.exercise.cardstore.data.repository

import app.cash.turbine.test
import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.data.local.CardDao
import com.exercise.cardstore.data.local.CardDaoFake
import com.exercise.cardstore.data.local.CardStoreDatabase
import com.exercise.cardstore.data.remote.CardStoreApi
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CardRepositoryImplTest {

    private var cardList = (1..10).map {
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

    private lateinit var repository: CardRepositoryImpl
    private lateinit var api: CardStoreApi
    private lateinit var db: CardStoreDatabase
    private lateinit var cardDao: CardDao

    @Before
    fun setUp() {
        api = mockk(relaxed = true) {
            coEvery { getCards() } returns mockk(relaxed = true)
        }
        cardDao = CardDaoFake()
        db = mockk(relaxed = true) {
            every { dao } returns cardDao
        }
        repository = CardRepositoryImpl(
            api = api,
            db = db
        )
    }

    @Test
    fun `Test local database data with fetch from remote set to true`() = runTest {
        val localCardList = (1..10).map {
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
        cardDao.insertCards(localCardList)

        repository.getCards(
            fetchFromRemote = true
        ).test {
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val cardListFromDb = awaitItem()
            assertThat(cardListFromDb is Resource.Success).isTrue()
            assertThat(cardListFromDb.data).isEqualTo(cardList.map { it })

            val remoteListFromDb = awaitItem()
            assertThat(remoteListFromDb is Resource.Success).isTrue()
            assertThat(remoteListFromDb.data).isEqualTo(
                cardDao.getCards().map { it }
            )

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }
}