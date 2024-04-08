package com.pr.servicesModule.presentation.paymentreminder.fragments.viewModels.home

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service

class HomeViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val services: List<Service> = emptyList(),
        val serviceNameHelperText: Boolean = false,
        val servicePriceHelperText: Boolean = false,
        val serviceCategoryHelperText: Boolean = false,
        val serviceDateHelperText: Boolean = false,
        val serviceTypeHelperText: Boolean = false,
        val serviceRememberHelperText: Boolean = false
    )

    sealed class UiIntent {
        data class AddEditService(val serviceId: String?, val action: String) : UiIntent()
        data class RemoveService(val serviceId: String) : UiIntent()
        data object GetServices : UiIntent()
    }

    sealed class UiAction {
        data class AddEditService(val serviceId: String?, val action: String) : UiAction()
        data object RemoveService : UiAction()
    }
}