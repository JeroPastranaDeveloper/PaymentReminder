package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PaymentsHistoryViewModel @Inject constructor(
    private val servicesForm: ServiceFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when(intent) {
            UiIntent.GetServices -> getServices()
        }
    }

    init {
        getServices()
    }

    private fun Service.getDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        return LocalDate.parse(this.date, formatter)
    }

    private fun getServices() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val services = servicesForm.getAllServicesForm()
            setState {
                copy(
                    services = services.orEmpty().sortedByDescending { it.getDate() },
                    isLoading = false
                )
            }
        }
    }
}