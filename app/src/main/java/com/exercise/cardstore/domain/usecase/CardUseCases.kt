package com.exercise.cardstore.domain.usecase

data class CardUseCases(
    val getCards: GetCards,
    val deleteCard: DeleteCard,
    val addCard: AddCard,
    val getCard: GetCard
)