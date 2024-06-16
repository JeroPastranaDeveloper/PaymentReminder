package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.ALL_CATEGORIES
import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState
import com.pr.paymentreminder.ui.theme.awaitAllAsyncJobs
import com.pr.paymentreminder.ui.theme.doIfTrue
import com.pr.paymentreminder.ui.theme.orElse
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
    private val getServicesUseCase: GetServicesUseCase,
    private val createServiceUseCase: CreateServiceUseCase,
    private val removeServiceUseCase: RemoveServiceUseCase,
    private val updateServiceUseCase: UpdateServiceUseCase,
    private val alarmScheduler: AlarmScheduler,
    private val preferencesHandler: PreferencesHandler,
    private val saveServiceForm: SaveServiceFormUseCase,
    private val getCategoriesUseCase: GetAllCategoryFormsUseCase,
    private val saveCategoryFormUseCase: SaveCategoryFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    private val sharedSnackBarType = SharedShowSnackBarType.sharedSnackBarTypeFlow

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AddEditService -> dispatchAction(
                UiAction.AddEditService(
                    intent.serviceId.orEmpty(),
                    intent.action
                )
            )

            UiIntent.CheckSnackBarConfig -> checkSnackBarConfig()
            is UiIntent.FilterCategory -> filterByCategory(intent.category)
            UiIntent.OnDismissSnackBar -> setState { copy(showSnackBar = false) }
            is UiIntent.RemoveService -> removeService(intent.service)
            is UiIntent.RestoreDeletedService -> restoreService(intent.service)
        }
    }

    private fun filterByCategory(category: String) {
        val services = state.value.servicesCopy
        val filteredServices = if (category == ALL_CATEGORIES) {
            services
        } else {
            services.filter { it.category == category }
        }

        setState { copy(services = filteredServices, selectedCategory = category) }
    }

    private fun checkSnackBarConfig() {
        viewModelScope.launch {
            getCategories()
            val snackBarType = sharedSnackBarType.firstOrNull() ?: CustomSnackBarType.NONE
            val showSnackBar =
                !(sharedSnackBarType.firstOrNull() == CustomSnackBarType.NONE || sharedSnackBarType.firstOrNull() == CustomSnackBarType.UPDATE_PAID)
            setState { copy(showSnackBarType = snackBarType, showSnackBar = showSnackBar) }
            delay(2000)
            SharedShowSnackBarType.resetSharedSnackBarType()
            setState { copy(showSnackBarType = CustomSnackBarType.NONE, showSnackBar = false) }
        }
    }

    init {
        viewModelScope.launch {
            if (preferencesHandler.hasToLogin) {
                getServices()
            }
        }
    }

    private fun createCategories(services: List<Service>) {
        (preferencesHandler.createCategories).doIfTrue {
            viewModelScope.launch {
                var categories = getCategoriesUseCase()
                if (services.isEmpty()) {
                    saveCategoryFormUseCase(Category(name = "Entertainment"))
                    saveCategoryFormUseCase(Category(name = "Platforms"))
                } else {
                    services.forEach { service ->
                        val existingCategory = categories?.find { it.name == service.category }
                        if (existingCategory == null) {
                            saveCategoryFormUseCase(Category(name = service.category))
                            categories = categories?.plus(Category(name = service.category))
                        }
                    }
                }
            }
        }.orElse {
            getCategories()
        }
        preferencesHandler.createCategories = false
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = getCategoriesUseCase()
            setState { copy(categories = categories ?: listOf(Category())) }
        }
    }

    private fun restoreService(service: Service) {
        viewModelScope.launch {
            createServiceUseCase(service.id, service)
        }
        setState { copy(showSnackBar = false, showSnackBarType = CustomSnackBarType.NONE) }
    }

    private fun Service.updateDate() {
        val today = LocalDate.now()
        if (this.getDate().isEqual(today) || this.getDate().isBefore(today)) {
            viewModelScope.launch {
                saveServiceForm(this@updateDate)
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

        viewModelScope.launch {
            getServicesUseCase().collect { services ->
                val servicesCopy = services.toList()

                awaitAllAsyncJobs(
                    { createCategories(services) },
                    { getCategories() },
                    {
                        servicesCopy.forEach { service ->
                            service.updateDate()
                            alarmScheduler.scheduleAlarm(service)
                            updateServiceUseCase(service.id, service.copy(isNotified = true))
                        }
                    }
                )

                preferencesHandler.firstTime = false

                setState {
                    copy(
                        services = services.sortedBy { it.getDate() },
                        servicesCopy = services.sortedBy { it.getDate() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateService(serviceId: String, newServiceData: Service) {
        viewModelScope.launch {
            updateServiceUseCase(serviceId, newServiceData)
        }
    }

    private fun removeService(service: Service) {
        viewModelScope.launch {
            removeServiceUseCase(service.id)
            setState {
                copy(
                    serviceToRemove = service,
                    showSnackBar = true,
                    showSnackBarType = CustomSnackBarType.DELETE
                )
            }
        }
    }
}