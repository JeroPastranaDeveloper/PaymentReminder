package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase,
    private val alarmScheduler: AlarmScheduler,
    preferencesHandler: PreferencesHandler,
    private val serviceForm: ServiceFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    private val sharedSnackBarType = SharedShowSnackBarType.sharedSnackBarTypeFlow

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AddEditService -> dispatchAction(UiAction.AddEditService(intent.serviceId.orEmpty(), intent.action))
            UiIntent.CheckSnackBarConfig -> checkSnackBarConfig()
            is UiIntent.RemoveService -> removeService(intent.service)
            is UiIntent.RestoreDeletedService -> restoreService(intent.service)
        }
    }

    private fun checkSnackBarConfig() {
        viewModelScope.launch {
            val snackBarType = sharedSnackBarType.firstOrNull() ?: CustomSnackBarType.NONE
            val showSnackBar = !(sharedSnackBarType.firstOrNull() == CustomSnackBarType.NONE || sharedSnackBarType.firstOrNull() == CustomSnackBarType.UPDATE_PAID)
            setState { copy(showSnackBarType = snackBarType, showSnackBar = showSnackBar) }
            delay(2000)
            SharedShowSnackBarType.resetSharedSnackBarType()
            setState { copy(showSnackBarType = CustomSnackBarType.NONE, showSnackBar = false) }
        }
    }

    init {
        if (preferencesHandler.hasToLogin) getServices()
    }

    private fun Service.getDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        return LocalDate.parse(this.date, formatter)
    }

    private fun restoreService(service: Service) {
        viewModelScope.launch {
            servicesUseCase.createService(service.id, service)
        }
        setState { copy(showSnackBar = false, showSnackBarType = CustomSnackBarType.NONE) }
    }

    private fun Service.updateDate() {
        val today = LocalDate.now()
        if (this.getDate().isEqual(today) || this.getDate().isBefore(today)) {
            viewModelScope.launch {
                serviceForm.setServiceForm(this@updateDate)
                when (this@updateDate.type) {
                    PaymentType.WEEKLY.type -> this@updateDate.date =
                        this@updateDate.getDate().plus(1, ChronoUnit.WEEKS).format(
                            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        )

                    PaymentType.MONTHLY.type -> this@updateDate.date =
                        this@updateDate.getDate().plus(1, ChronoUnit.MONTHS).format(
                            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        )

                    PaymentType.YEARLY.type -> this@updateDate.date =
                        this@updateDate.getDate().plus(1, ChronoUnit.YEARS).format(
                            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        )
                }
                updateService(this@updateDate.id, this@updateDate)
            }
        }
    }

    private fun getServices() {
        setState {
            copy(
                isLoading = true
            )
        }

        /**
         * The second of wait is to complete the login process.
         */
        viewModelScope.launch {
            delay(1000)

            servicesUseCase.getServices().collect { services ->
                services.forEach { service ->
                    service.updateDate()
                    alarmScheduler.scheduleAlarm(service)
                }

                setState {
                    copy(
                        services = services.sortedBy { it.getDate() },
                        isLoading = false
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

    private fun removeService(service: Service) {
        setState { copy(showSnackBar = false) }
        viewModelScope.launch {
            setState { copy(serviceToRemove = service, showSnackBar = true, showSnackBarType = CustomSnackBarType.DELETE) }
            servicesUseCase.removeService(service.id)

            delay(2000)
            setState { copy(showSnackBar = false) }
        }
    }
}