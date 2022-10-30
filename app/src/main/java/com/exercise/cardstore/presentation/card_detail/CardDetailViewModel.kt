package com.exercise.cardstore.presentation.card_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.exercise.cardstore.core.util.Resource
import com.exercise.cardstore.di.IoDispatcher
import com.exercise.cardstore.domain.usecase.CardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    private val cardUseCases: CardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CardDetailState())
    val state: State<CardDetailState> = _state

    var currentCardId: Int?

    init {
        currentCardId = savedStateHandle.get<Int>("cardId")
        getCardDetails()
    }

    private fun getCardDetails() {
        viewModelScope.launch {
            val cardId = currentCardId ?: return@launch
            _state.value = state.value.copy(
                isLoading = true
            )
            val cardDetails = async {  cardUseCases.getCard(cardId) }
            when(val result = cardDetails.await()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        card = result.data,
                        isLoading = false
                    )
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
}