package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString

class GraphicViewContract : BaseViewContract() {
    data class UiState(
        val services: List<Service> = emptyList(),
        val weeklyExpenditure: String? = emptyString(),
        val monthlyExpenditure: String? = emptyString(),
        val yearlyExpenditure: String? = emptyString(),
    )

    sealed class UiIntent {
        data class GetFilteredServices(val filter: String) : UiIntent()
    }

    sealed class UiAction {

    }
}