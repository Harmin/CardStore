package com.exercise.cardstore.presentation.card_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exercise.cardstore.R
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType
import com.exercise.cardstore.presentation.util.components.LoadingIndicator
import com.exercise.cardstore.presentation.util.components.PressIconButton
import com.exercise.cardstore.ui.theme.hexInt

@Composable
fun CardDetailScreen(
    navController: NavController,
    cardColor: Int,
    viewModel: CardDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val backgroundColor = if(cardColor == -1) MaterialTheme.colorScheme.primary else Color(cardColor.hexInt())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        state.card?.let { card ->
            Text(text = card.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.cardNumber, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: ${card.firstName} ${card.lastName}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Expiry: ${card.expiryDate}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            PressIconButton(
                onClick = {},
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                text = { Text(stringResource(R.string.show_qr)) }
            )

            if (state.isLoading) {
                LoadingIndicator()
            }
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }

    BackHandler {
        navController.popBackStack()
    }
}