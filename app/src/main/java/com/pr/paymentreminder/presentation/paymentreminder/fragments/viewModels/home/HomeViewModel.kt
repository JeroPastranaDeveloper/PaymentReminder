package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.androidVersions.hasT33
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.providers.Permissions
import com.pr.paymentreminder.providers.PermissionsRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase,
    private val alarmScheduler: AlarmScheduler,
    private val permissionsRequester: PermissionsRequester
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.GetServices -> getServices()
            is UiIntent.AddEditService -> dispatchAction(UiAction.AddEditService(intent.serviceId.orEmpty(), intent.action))
            is UiIntent.RemoveService -> removeService(intent.serviceId)
        }
    }

    init {
        getServices()
        if(hasT33()) checkPermissions()
    }

    private fun checkPermissions() {
        viewModelScope.launch {
            // permissionsRequester.requestPermissions(Permissions.Notification)
        }
    }

    private fun Service.getDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        return LocalDate.parse(this.date, formatter)
    }

    private fun Service.updateDate() {
        val today = LocalDate.now()
        if (this.getDate().isEqual(today) || this.getDate().isBefore(today)) {
            when (this.type) {
                PaymentType.WEEKLY.type -> this.date = this.getDate().plus(1, ChronoUnit.WEEKS).format(
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                PaymentType.MONTHLY.type -> this.date = this.getDate().plus(1, ChronoUnit.MONTHS).format(
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                PaymentType.YEARLY.type -> this.date = this.getDate().plus(1, ChronoUnit.YEARS).format(
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
            }
            updateService(this.id, this)
        }
    }

    private fun getServices() {
        setState {
            copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            servicesUseCase.getServices().collect { services ->
                services.forEach { service ->
                    service.updateDate()
                    alarmScheduler.scheduleAlarm(service)
                }

                setState {
                    copy(
                        isLoading = false,
                        services = services.sortedBy { it.getDate() }
                    )
                }
            }
        }
    }

    private fun updateService(serviceId: String, newServiceData: Service) {
        viewModelScope.launch {
            servicesUseCase.updateService(serviceId, newServiceData)
        }
    }

    private fun removeService(serviceId: String) {
        viewModelScope.launch {
            servicesUseCase.deleteService(serviceId)
        }
        dispatchAction(UiAction.RemoveService)
    }
}