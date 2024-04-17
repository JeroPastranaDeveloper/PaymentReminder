package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service

class PaymentsHistoryViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val services: List<Service> = emptyList(),
    )

    sealed class UiIntent {
        data object GetServices : UiIntent()
    }

    sealed class UiAction {
    }
}