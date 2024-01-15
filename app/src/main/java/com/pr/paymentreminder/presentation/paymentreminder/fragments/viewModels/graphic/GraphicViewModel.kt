package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiIntent
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiState
import  com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiAction
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.GetFilteredServices -> filterServices(intent.filter)
        }
    }

    init {
        filterServices(Constants.ALL_SERVICES)
    }

    private fun filterServices(filter: String) {
        viewModelScope.launch {
            servicesUseCase.getServices().collect { services ->
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