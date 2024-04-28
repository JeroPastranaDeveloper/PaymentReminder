package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Permissions

class PaymentReminderViewContract : BaseViewContract() {
    data class UiState(
        val exactAlarmGranted: Boolean = false,
        val notificationsGranted: Boolean = false,
        val showDialog: Boolean = false
    )

    sealed class UiIntent {
        data object CheckNotifications : UiIntent()
        data class NotificationsGranted(val permission: Permissions) : UiIntent()
        data class ShowCloseApp(val hasToShow: Boolean) : UiIntent()
    }

    sealed class UiAction
}