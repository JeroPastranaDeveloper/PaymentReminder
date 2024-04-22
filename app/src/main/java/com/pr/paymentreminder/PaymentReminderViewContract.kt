package com.pr.paymentreminder

import com.pr.paymentreminder.base.BaseViewContract

class PaymentReminderViewContract : BaseViewContract() {
    data class UiState(
        val showDialog: Boolean = false
    )

    sealed class UiIntent {
        data class ShowCloseApp(val hasToShow: Boolean) : UiIntent()
    }

    sealed class UiAction
}