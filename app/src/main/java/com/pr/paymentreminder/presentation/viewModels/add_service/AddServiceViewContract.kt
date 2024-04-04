package com.pr.paymentreminder.presentation.viewModels.add_service

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.ui.theme.emptyString

class AddServiceViewContract : BaseViewContract() {
    data class UiState(
        val action: String = emptyString(),
        val categoryHelperText: Boolean = false,
        val dateHelperText: Boolean = false,
        val isLoading: Boolean = false,
        val nameHelperText: Boolean = false,
        val priceHelperText: Boolean = false,
        val rememberHelperText: Boolean = false,
        val service: Service = Service(),
        val serviceId: String = emptyString(),
        val typeHelperText: Boolean = false
    )

    sealed class UiIntent {
        data class CheckIntent(val serviceId: String, val action: String) : UiIntent()
        data class ValidateAndSave(val service: Service) : UiIntent()
        data class ValidateService(val item: ServiceItem, val value: String) : UiIntent()
    }

    sealed class  UiAction {
        data object GoBack : UiAction()
    }
}