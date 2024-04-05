package com.pr.paymentreminder.presentation.paymentreminder

import androidx.lifecycle.ViewModel
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract
import com.pr.paymentreminder.providers.PermissionsRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentReminderViewModel @Inject constructor(
    private val permissionsRequester: PermissionsRequester
) : BaseComposeViewModelWithActions<PaymentReminderViewContract.UiState, PaymentReminderViewContract.UiIntent, PaymentReminderViewContract.UiAction>() {

    override val initialViewState = PaymentReminderViewContract.UiState()
    override fun manageIntent(intent: PaymentReminderViewContract.UiIntent) {
        when(intent) {
            PaymentReminderViewContract.UiIntent.CheckIntent -> permissionsRequester.onCreate()
        }
    }
}
