package com.pr.paymentreminder.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<UiState, UiIntent> : ViewModel() {

    protected abstract val _state: MutableStateFlow<UiState>
    abstract val state: StateFlow<UiState>

    protected abstract fun manageIntent(intent: UiIntent)

    fun sendIntent(intent: UiIntent) {
        manageIntent(intent)
    }
}