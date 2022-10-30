package com.exercise.cardstore.presentation.card_detail

import androidx.lifecycle.SavedStateHandle
import com.exercise.cardstore.MainCoroutineRule
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.data.repository.CardRepositoryFake
import com.exercise.cardstore.domain.usecase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CardDetailViewModeltest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CardDetailViewModel
    private lateinit var repositoryFake: CardRepositoryFake
    private lateinit var cardUseCases: CardUseCases

    @Before
    fun setUp() {
        repositoryFake = CardRepositoryFake()
        cardUseCases = CardUseCases(
            getCards = GetCards(repositoryFake),
            deleteCard = DeleteCard(repositoryFake),
            addCard = AddCard(repositoryFake),
            getCard = GetCard(repositoryFake)
        )
        viewModel = CardDetailViewModel(
            cardUseCases,
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    "cardId" to 1
                )
            )
        )
    }

    @Test
    fun `GetCard Details mapped to state`() {
        val card = Card(
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
        repositoryFake.cardDetails = card

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.card).isEqualTo(card)
    }
}