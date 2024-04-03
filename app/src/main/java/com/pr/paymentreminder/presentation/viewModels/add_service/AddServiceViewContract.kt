package com.pr.paymentreminder.presentation.viewModels.add_service

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceHelperText
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.ui.theme.emptyString

class AddServiceViewContract : BaseViewContract() {
    data class UiState(
        val action: String = emptyString(),
        val isLoading: Boolean = false,
        val service: Service = Service(),
        val serviceHelperText: ServiceHelperText? = null,
        val serviceId: String = emptyString()
    )

    sealed class UiIntent {
        data class CheckIntent(val serviceId: String, val action: String) : UiIntent()
        data class CreateService(val service: Service) : UiIntent()
        data class UpdateService(val serviceId: String, val service: Service) : UiIntent()
        data class ValidateService(val item: ServiceItem, val value: String) : UiIntent()
    }

    sealed class  UiAction {
        data object GoBack : UiAction()
    }
}