package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiAction
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.model.Permissions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentReminderViewModel @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.CheckNotifications -> checkNotifications()
            is UiIntent.NotificationsGranted -> notificationsGranted(intent.permission)
            is UiIntent.ShowCloseApp -> setState { copy(showDialog = intent.hasToShow) }
        }
    }

    private fun checkNotifications() {
        setState { copy(
            exactAlarmGranted = preferencesHandler.exactAlarmGranted,
            notificationsGranted = preferencesHandler.notificationsGranted
        ) }
    }

    private fun notificationsGranted(permission: Permissions) {
        when (permission) {
            Permissions.EXACT_ALARM -> {
                preferencesHandler.exactAlarmGranted = true
                setState { copy(exactAlarmGranted = true) }
            }
            Permissions.NOTIFICATIONS -> {
                preferencesHandler.notificationsGranted = true
                setState { copy(notificationsGranted = true) }
            }
        }
    }
}