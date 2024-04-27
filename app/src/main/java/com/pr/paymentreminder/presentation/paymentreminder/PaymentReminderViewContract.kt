package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.base.BaseViewContract

class PaymentReminderViewContract : BaseViewContract() {
    data class UiState(
        val notificationsGranted: Boolean = false,
        val showDialog: Boolean = false
    )

    sealed class UiIntent {
        data object CheckNotifications : UiIntent()
        data object NotificationsGranted : UiIntent()
        data class ShowCloseApp(val hasToShow: Boolean) : UiIntent()
    }

    sealed class UiAction
}