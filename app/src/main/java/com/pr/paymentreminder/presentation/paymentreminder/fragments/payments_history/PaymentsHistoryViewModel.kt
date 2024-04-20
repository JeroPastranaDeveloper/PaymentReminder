package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiState
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
            UiIntent.CloseRemoveServiceDialog -> setState { copy(showRemoveServiceDialog = false) }
            UiIntent.DeleteService -> deleteService()
            is UiIntent.EditService -> dispatchAction(UiAction.EditService(intent.serviceId))
            UiIntent.GetServices -> getServices()
            is UiIntent.ShowDeleteServiceDialog -> showServiceRemoveDialog(intent.serviceId)
        }
    }

    private fun deleteService() {
        viewModelScope.launch {
            servicesForm.removeService(state.value.serviceToRemove.id)
            setState { copy(serviceToRemove = Service(), showRemoveServiceDialog = false) }
            getServices()
        }
    }

    private fun showServiceRemoveDialog(serviceId: String) {
        viewModelScope.launch {
            val service = servicesForm.getServiceForm(serviceId)
            setState { copy(showRemoveServiceDialog = true, serviceToRemove = service ?: Service()) }
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