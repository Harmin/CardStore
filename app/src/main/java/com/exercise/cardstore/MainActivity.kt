package com.exercise.cardstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exercise.cardstore.presentation.card_detail.CardDetailScreen
import com.exercise.cardstore.presentation.cards.CardsScreen
import com.exercise.cardstore.presentation.login.LoginScreen
import com.exercise.cardstore.presentation.util.Screen
import com.exercise.cardstore.ui.theme.CardStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CardStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun AppNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screen.CardsScreen.route) {
                CardsScreen(navController = navController)
            }
            composable(
                route = Screen.CardDetailScreen.route +
                        "?cardId={cardId}&cardColor={cardColor}",
                arguments = listOf(
                    navArgument(
                        name = "cardId"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "cardColor"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                )
            ) {
                val color = it.arguments?.getInt("cardColor") ?: -1
                CardDetailScreen(
                    navController = navController,
                    cardColor = color
                )
            }
        }
    }
}