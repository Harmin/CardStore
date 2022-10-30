package com.exercise.cardstore.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
