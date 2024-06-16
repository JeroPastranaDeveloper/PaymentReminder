package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.ALL_CATEGORIES
import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.Service

class HomeViewContract : BaseViewContract() {
    data class UiState(
        val categories: List<Category> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCategory: String = ALL_CATEGORIES,
        val services: List<Service> = emptyList(),
        val servicesCopy: List<Service> = emptyList(),
        val serviceToRemove: Service = Service(),
        val showSnackBarType: CustomSnackBarType = CustomSnackBarType.NONE,
        val showSnackBar: Boolean = false
    )

    sealed class UiIntent {
        data class AddEditService(val serviceId: String?, val action: String) : UiIntent()
        data object CheckSnackBarConfig : UiIntent()
        data class FilterCategory(val category: String) : UiIntent()
        data object OnDismissSnackBar : UiIntent()
        data class RemoveService(val service: Service) : UiIntent()
        data class RestoreDeletedService(val service: Service) : UiIntent()
    }

    sealed class UiAction {
        data class AddEditService(val serviceId: String?, val action: String) : UiAction()
    }
}