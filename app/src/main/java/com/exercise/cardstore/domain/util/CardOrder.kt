package com.exercise.cardstore.domain.util

sealed class CardOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): CardOrder(orderType)
    class Date(orderType: OrderType): CardOrder(orderType)
    class Color(orderType: OrderType): CardOrder(orderType)

    fun copy(orderType: OrderType): CardOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
