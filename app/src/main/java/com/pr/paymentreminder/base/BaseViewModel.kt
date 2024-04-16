package com.pr.paymentreminder.base

import androidx.lifecycle.ViewModel
import com.pr.paymentreminder.data.model.CustomSnackBarType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<UiState, UiIntent> : ViewModel() {

    protected abstract val _state: MutableStateFlow<UiState>
    abstract val state: StateFlow<UiState>

    protected abstract fun manageIntent(intent: UiIntent)

    object SharedShowSnackBarType {
        private val _showSnackBarType = MutableStateFlow(CustomSnackBarType.NONE)
        val sharedSnackBarTypeFlow: StateFlow<CustomSnackBarType> get() = _showSnackBarType

        fun updateSharedSnackBarType(newValue: CustomSnackBarType) {
            _showSnackBarType.value = newValue
        }
    }

    fun sendIntent(intent: UiIntent) {
        manageIntent(intent)
    }
}