package com.pr.paymentreminder.presentation.paymentreminder

import com.pr.paymentreminder.PaymentReminderViewContract.UiIntent
import com.pr.paymentreminder.PaymentReminderViewContract.UiState
import com.pr.paymentreminder.PaymentReminderViewContract.UiAction
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions

class PaymentReminderViewModel : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ShowCloseApp -> setState { copy(showDialog = intent.hasToShow) }
        }
    }
}