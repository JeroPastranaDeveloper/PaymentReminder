package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString

class PaymentsHistoryViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val showRemoveServiceDialog: Boolean = false,
        val serviceToRemove: Service = Service(),
        val services: List<Service> = emptyList()
    )

    sealed class UiIntent {
        data object DeleteService : UiIntent()
        data class EditService(val serviceId: String) : UiIntent()
        data object GetServices : UiIntent()
        data class ShowDeleteServiceDialog(val serviceId: String = emptyString(), val hasToShow: Boolean) : UiIntent()
    }

    sealed class UiAction {
        data class EditService(val serviceId: String) : UiAction()
    }
}