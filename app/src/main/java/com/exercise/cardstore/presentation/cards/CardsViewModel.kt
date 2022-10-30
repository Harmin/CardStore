package com.exercise.cardstore.presentation.cards

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.core.util.sortBy
import com.exercise.cardstore.di.IoDispatcher
import com.exercise.cardstore.data.local.Card
import com.exercise.cardstore.domain.usecase.CardUseCases
import com.exercise.cardstore.domain.util.CardOrder
import com.exercise.cardstore.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val cardUseCases: CardUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = mutableStateOf(CardsState())
    val state: State<CardsState> = _state

    private var recentlyDeletedCard: Card? = null

    fun onEvent(event: CardsEvent) {
        when (event) {
            is CardsEvent.Order -> {
                if (state.value.cardOrder::class == event.cardOrder::class &&
                    state.value.cardOrder.orderType == event.cardOrder.orderType
                ) {
                    return
                }
                getCards(cardOrder = event.cardOrder)
            }
            is CardsEvent.DeleteCard -> {
                viewModelScope.launch {
                    cardUseCases.deleteCard(event.card)
                    recentlyDeletedCard = event.card
                    getCards(cardOrder = state.value.cardOrder)
                }
            }
            is CardsEvent.RestoreCard -> {
                viewModelScope.launch {
                    cardUseCases.addCard(recentlyDeletedCard ?: return@launch)
                    recentlyDeletedCard = null
                    getCards(cardOrder = state.value.cardOrder)
                }
            }
            is CardsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun getCards(fetchFromRemote: Boolean = false, cardOrder: CardOrder) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                cardUseCases.getCards(fetchFromRemote)
                    .collect { result ->
                    setResult(result, cardOrder)
                }
            }
        }
    }

    private fun setResult(result: Resource<List<Card>>, cardOrder: CardOrder) {
        when (result) {
            is Resource.Success -> {
                result.data?.let { cards ->
                    _state.value = state.value.copy(
                        cards = cards.sortBy(cardOrder),
                        cardOrder = cardOrder
                    )
                }
            }
            is Resource.Error -> {
                _state.value = state.value.copy(
                    error = result.message ?: "Couldn't load"
                )
            }
            is Resource.Loading -> {
                _state.value = state.value.copy(
                    isLoading = result.isLoading
                )
            }
        }
    }
}