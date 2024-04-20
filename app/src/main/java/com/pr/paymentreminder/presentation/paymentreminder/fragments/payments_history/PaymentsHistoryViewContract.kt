package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service

class PaymentsHistoryViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val showRemoveServiceDialog: Boolean = false,
        val serviceToRemove: Service = Service(),
        val services: List<Service> = emptyList()
    )

    sealed class UiIntent {
        data object CloseRemoveServiceDialog : UiIntent()
        data object DeleteService : UiIntent()
        data class EditService(val serviceId: String) : UiIntent()
        data object GetServices : UiIntent()
        data class ShowDeleteServiceDialog(val serviceId: String) : UiIntent()
    }

    sealed class UiAction {
        data class EditService(val serviceId: String) : UiAction()
    }
}