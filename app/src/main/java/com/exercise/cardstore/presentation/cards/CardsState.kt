package com.exercise.cardstore.presentation.cards

import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType

data class CardsState(
    val cards: List<Card> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val cardOrder: CardOrder = CardOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
