package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.ui.theme.emptyString

class PaymentReminderViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false
    )

    sealed class UiIntent {
        data object CheckIntent : UiIntent()
    }

    sealed class  UiAction {
    }
}