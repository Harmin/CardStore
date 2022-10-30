package com.exercise.cardstore.presentation.util

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object CardsScreen: Screen("cards_screen")
    object CardDetailScreen: Screen("card_detail_screen")
}
