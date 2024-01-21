package com.pr.paymentreminder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, UiIntent> : ViewModel() {

    protected abstract val _state: MutableStateFlow<UiState>
    abstract val state: StateFlow<UiState>
    private val vmIntent = Channel<UiIntent>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            vmIntent.consumeAsFlow().collect { intent ->
                manageIntent(intent)
            }
        }
    }

    protected abstract suspend fun manageIntent(intent: UiIntent)

    fun sendIntent(intent: UiIntent) {
        viewModelScope.launch {
            vmIntent.send(intent)
        }
    }
}