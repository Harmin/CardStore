package com.exercise.cardstore.core.util

import android.content.res.AssetManager
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType

fun AssetManager.readAssetsFile(): String = open("cards.json").bufferedReader().use{it.readText()}

fun List<Card>.sortBy(cardOrder: CardOrder): List<Card> {
    return this.let { cards ->
        when(cardOrder.orderType) {
            is OrderType.Ascending -> {
                when(cardOrder) {
                    is CardOrder.Title -> cards.sortedBy { it.title.lowercase() }
                    is CardOrder.Date -> cards.sortedBy { it.timestamp }
                    is CardOrder.Color -> cards.sortedBy { it.color }
                }
            }
            is OrderType.Descending -> {
                when(cardOrder) {
                    is CardOrder.Title -> cards.sortedByDescending { it.title.lowercase() }
                    is CardOrder.Date -> cards.sortedByDescending { it.timestamp }
                    is CardOrder.Color -> cards.sortedByDescending { it.color }
                }
            }
        }
    }
}