package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Service

class GraphicViewContract : BaseViewContract() {
    data class UiState(
        val services: List<Service> = emptyList(),
    )

    sealed class UiIntent {
        data class GetFilteredServices(val filter: String) : UiIntent()
    }

    sealed class UiAction {

    }
}