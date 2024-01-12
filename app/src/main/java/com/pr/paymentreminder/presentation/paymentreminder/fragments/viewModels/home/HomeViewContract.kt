package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home

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
        val serviceRememberHelperText: Boolean = false,
    )

    sealed class UiIntent {
        data class RemoveService(val serviceId: String) : UiIntent()
        data class UpdateService(val serviceId: String, val service: Service) : UiIntent()
        data class CreateService(val service: Service) : UiIntent()
        data class ValidateServiceName(val serviceName: String) : UiIntent()
        data class ValidateServicePrice(val servicePrice: String) : UiIntent()
        data class ValidateServiceCategory(val selectedCategory: String) : UiIntent()
        data class ValidateServiceDate(val serviceDate: String) : UiIntent()
        data class ValidateServiceType(val selectedPaymentType: String) : UiIntent()
        data class ValidateServiceRemember(val selectedRemember: String) : UiIntent()
        object GetServices : UiIntent()
    }

    sealed class UiAction {
        object RemoveService : UiAction()
    }
}