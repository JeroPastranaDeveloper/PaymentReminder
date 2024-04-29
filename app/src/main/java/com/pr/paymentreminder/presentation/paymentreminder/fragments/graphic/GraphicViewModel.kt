package com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewContract.UiIntent
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewContract.UiState
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewContract.UiAction
import com.pr.paymentreminder.domain.usecase.service.ServicesUseCase
import com.pr.paymentreminder.ui.theme.showTwoDecimals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.GetFilteredServices -> filterServices(intent.filter)
        }
    }

    init {
        filterServices(Constants.ALL_SERVICES)
    }

    private fun calculateExpenditure(services: List<Service>) {
        val weeklyServices = services.filter { it.type == Constants.WEEKLY }.sumOf { it.price.toDouble() }
        val monthlyServices = services.filter { it.type == Constants.MONTHLY }.sumOf { it.price.toDouble() }
        val yearlyServices = services.filter { it.type == Constants.YEARLY }.sumOf { it.price.toDouble() }

        val monthlyExpenditure = (weeklyServices * 4.5) + monthlyServices
        val yearlyExpenditure = (monthlyExpenditure * 12) + yearlyServices

        setState {
            copy(
                weeklyExpenditure = weeklyServices.showTwoDecimals(),
                monthlyExpenditure = monthlyServices.showTwoDecimals(),
                monthlyTotalExpenditure = monthlyExpenditure.showTwoDecimals(),
                yearlyExpenditure = yearlyServices.showTwoDecimals(),
                yearlyTotalExpenditure = yearlyExpenditure.showTwoDecimals()
            )
        }
    }

    private fun filterServices(filter: String) {
        viewModelScope.launch {
            servicesUseCase.getServices().collect { services ->
                calculateExpenditure(services)
                setState {
                    copy(services = if (filter == Constants.ALL_SERVICES) {
                        services
                    } else {
                        services.filter { it.type.contains(filter) }
                    })
                }
            }
        }
    }
}