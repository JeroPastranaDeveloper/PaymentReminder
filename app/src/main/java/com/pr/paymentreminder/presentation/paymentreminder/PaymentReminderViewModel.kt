package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiAction
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
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
            UiIntent.NotificationsGranted -> notificationsGranted()
            is UiIntent.ShowCloseApp -> setState { copy(showDialog = intent.hasToShow) }
        }
    }

    private fun checkNotifications() {
        setState { copy(notificationsGranted = preferencesHandler.notificationsGranted) }
    }

    private fun notificationsGranted() {
        preferencesHandler.notificationsGranted = true
        setState { copy(notificationsGranted = true) }
    }
}