package com.pr.paymentreminder.presentation.viewModels.add_service

import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor() : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when(intent) {
            is UiIntent.CreateService -> TODO()
            is UiIntent.UpdateService -> TODO()
            is UiIntent.ValidateService -> TODO()
        }
    }
}