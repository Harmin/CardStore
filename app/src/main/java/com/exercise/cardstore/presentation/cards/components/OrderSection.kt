package com.exercise.cardstore.presentation.cards.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    cardOrder: CardOrder = CardOrder.Date(OrderType.Descending),
    onOrderChange: (CardOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = cardOrder is CardOrder.Title,
                onSelect = { onOrderChange(CardOrder.Title(cardOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = cardOrder is CardOrder.Date,
                onSelect = { onOrderChange(CardOrder.Date(cardOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = cardOrder is CardOrder.Color,
                onSelect = { onOrderChange(CardOrder.Color(cardOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = cardOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(cardOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = cardOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(cardOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}