package com.exercise.cardstore.presentation.cards

import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.util.CardOrder

sealed class CardsEvent {
    data class Order(val cardOrder: CardOrder): CardsEvent()
    data class DeleteCard(val card: Card): CardsEvent()
    object RestoreCard: CardsEvent()
    object ToggleOrderSection: CardsEvent()
}
