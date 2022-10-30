package com.exercise.cardstore.presentation.card_detail

import com.exercise.cardstore.data.local.Card

data class CardDetailState(
    val card: Card? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
