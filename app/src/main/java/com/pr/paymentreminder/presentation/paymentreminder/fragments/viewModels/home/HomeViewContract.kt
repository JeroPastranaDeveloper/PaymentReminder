package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.CustomToastInfo
import com.pr.paymentreminder.data.model.Service

class HomeViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val serviceToRemove: Service = Service(),
        val services: List<Service> = emptyList(),
        val showSnackBar: Boolean = false
    )

    sealed class UiIntent {
        data class AddEditService(val serviceId: String?, val action: String) : UiIntent()
        data class RemoveService(val service: Service) : UiIntent()
        data class RestoreDeletedService(val service: Service) : UiIntent()
    }

    sealed class UiAction {
        data class AddEditService(val serviceId: String?, val action: String) : UiAction()
        data class ShowRemovedToast(val toastInfo: CustomToastInfo) : UiAction()
    }
}