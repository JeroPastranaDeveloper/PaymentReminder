package com.pr.paymentreminder.presentation.viewModels.add_service

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem

class AddServiceViewContract : BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val service: Service = Service(),
        val serviceNameHelperText: Boolean = false,
        val servicePriceHelperText: Boolean = false,
        val serviceCategoryHelperText: Boolean = false,
        val serviceDateHelperText: Boolean = false,
        val serviceTypeHelperText: Boolean = false,
        val serviceRememberHelperText: Boolean = false,
    )

    sealed class UiIntent {
        data class GetService(val serviceId: String) : UiIntent()
        data class UpdateService(val serviceId: String, val service: Service) : UiIntent()
        data class CreateService(val service: Service) : UiIntent()
        data class ValidateService(val item: ServiceItem, val value: String) : UiIntent()
    }

    sealed class  UiAction {

    }
}