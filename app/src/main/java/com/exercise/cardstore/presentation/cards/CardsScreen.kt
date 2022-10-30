package com.exercise.cardstore.presentation.cards

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.exercise.cardstore.R
import com.exercise.cardstore.core.util.TestTags
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType
import com.exercise.cardstore.presentation.cards.components.CardItem
import com.exercise.cardstore.presentation.cards.components.OrderSection
import com.exercise.cardstore.presentation.util.Screen
import com.exercise.cardstore.presentation.util.components.LoadingIndicator
import com.exercise.cardstore.presentation.util.components.showErrorDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun CardsScreen(
    navController: NavController,
    viewModel: CardsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getCards(true, CardOrder.Date(OrderType.Descending))
    }

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.your_cards),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(CardsEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = stringResource(R.string.sort)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .testTag(TestTags.ORDER_SECTION),
                        cardOrder = state.cardOrder,
                        onOrderChange = {
                            viewModel.onEvent(CardsEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (state.isLoading) {
                    LoadingIndicator()
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.cards) { card ->
                        StoreCard(card, viewModel, navController, scope, snackbarHostState)
                    }
                }
            }
        }
    )

    BackHandler {
        navController.clearBackStack(navController.graph.startDestinationId)
    }
}

@Composable
fun StoreCard(
    card: Card,
    viewModel: CardsViewModel,
    navController: NavController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val isCardDisabled = (card.id == 1)
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        if (isCardDisabled) {
            showErrorDialog(showDialog, stringResource(R.string.card_expired))
        } else {
           showCardDetailsScreen(navController, card.id, card.color)
        }
    }

    CardItem(
        card = card,
        isCardDisabled = isCardDisabled,
        modifier = Modifier
            .fillMaxWidth()
            .clickable  {showDialog.value = true},
        onDeleteClick = {
            viewModel.onEvent(CardsEvent.DeleteCard(card))
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = "Card deleted",
                    actionLabel = "Undo"
                )
                if(result == SnackbarResult.ActionPerformed) {
                    viewModel.onEvent(CardsEvent.RestoreCard)
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
}

fun showCardDetailsScreen(navController: NavController, cardId: Int?, cardColor: Int) {
    navController.navigate(
        Screen.CardDetailScreen.route +
                "?cardId=${cardId}&cardColor=${cardColor}"
    ){
        restoreState = true
        popUpTo(Screen.CardsScreen.route) {
            saveState = true
        }
    }
}
