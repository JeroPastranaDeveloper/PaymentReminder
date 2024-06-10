package com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic

import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString

class GraphicViewContract : BaseViewContract() {
    data class UiState(
        val services: List<Service> = emptyList(),
        val weeklyExpenditure: String? = emptyString(),
        val monthlyExpenditure: String? = emptyString(),
        val monthlyTotalExpenditure: String? = emptyString(),
        val selectedChip: String = emptyString(),
        val serviceTypes: List<Pair<Int, String>> = listOf(
            Pair(R.string.all_services, Constants.ALL_SERVICES),
            Pair(R.string.weekly_services, PaymentType.WEEKLY.type),
            Pair(R.string.monthly_services, PaymentType.MONTHLY.type),
            Pair(R.string.yearly_services, PaymentType.YEARLY.type)
        ),
        val yearlyExpenditure: String? = emptyString(),
        val yearlyTotalExpenditure: String? = emptyString()
    )

    sealed class UiIntent {
        data class GetFilteredServices(val filter: String) : UiIntent()
    }

    sealed class UiAction {

    }
}