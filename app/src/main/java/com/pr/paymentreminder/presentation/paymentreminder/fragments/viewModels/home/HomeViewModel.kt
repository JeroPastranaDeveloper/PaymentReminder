package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase,
    private val alarmScheduler: AlarmScheduler
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.RemoveService -> removeService(intent.serviceId)
            is UiIntent.CreateService -> createService(intent.service)
            is UiIntent.UpdateService -> updateService(intent.serviceId, intent.service)
            is UiIntent.ValidateService -> validateServiceItem(intent.item, intent.value)
            UiIntent.GetServices -> getServices()
        }
    }

    init {
        getServices()
    }

    private fun createService(service: Service) {
        viewModelScope.launch {
            val database = Firebase.database
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val servicesRef = database.getReference("$userId/${Constants.SERVICES}")
            val id = servicesRef.push().key
            if (id != null) {
                service.id = id
                servicesUseCase.createService(id, service)
            }
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

    private fun validateServiceItem(item: ServiceItem, value: String) {
        val isEmpty = value.isEmpty()
        when (item) {
            ServiceItem.NAME -> setState { copy(serviceNameHelperText = isEmpty) }
            ServiceItem.CATEGORY -> setState { copy(serviceCategoryHelperText = isEmpty) }
            ServiceItem.DATE -> setState { copy(serviceDateHelperText = isEmpty) }
            ServiceItem.TYPE -> setState { copy(serviceTypeHelperText = isEmpty) }
            ServiceItem.PRICE -> setState { copy(servicePriceHelperText = isEmpty) }
            ServiceItem.REMEMBER -> setState { copy(serviceRememberHelperText = isEmpty) }
        }
    }
}