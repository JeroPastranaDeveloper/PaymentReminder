package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.service_form.GetAllServiceFormsUseCase
import com.pr.paymentreminder.domain.usecase.service_form.GetServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.service_form.RemoveServiceFormUseCase
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
    private val removeServiceForm: RemoveServiceFormUseCase,
    private val getServiceForm: GetServiceFormUseCase,
    private val getAllServiceForms: GetAllServiceFormsUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when(intent) {
            UiIntent.DeleteService -> deleteService()
            is UiIntent.EditService -> dispatchAction(UiAction.EditService(intent.serviceId))
            UiIntent.GetServices -> getServices()
            is UiIntent.ShowDeleteServiceDialog -> showServiceRemoveDialog(intent.serviceId, intent.hasToShow)
        }
    }

    private fun deleteService() {
        viewModelScope.launch {
            removeServiceForm(state.value.serviceToRemove.id)
            setState { copy(serviceToRemove = Service(), showRemoveServiceDialog = false) }
            getServices()
        }
    }

    private fun showServiceRemoveDialog(serviceId: String, hasToShow: Boolean) {
        viewModelScope.launch {
            if (hasToShow) {
                val service = getServiceForm(serviceId)
                setState {
                    copy(
                        showRemoveServiceDialog = true,
                        serviceToRemove = service ?: Service()
                    )
                }
            } else {
                setState { copy(showRemoveServiceDialog = false) }
            }
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
            val services = getAllServiceForms()
            setState {
                copy(
                    services = services.orEmpty().sortedByDescending { it.getDate() },
                    isLoading = false
                )
            }
        }
    }
}