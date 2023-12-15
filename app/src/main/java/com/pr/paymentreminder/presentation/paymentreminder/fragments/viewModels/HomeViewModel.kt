package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    private val _serviceNameHelperText = MutableStateFlow<String?>(null)
    val serviceNameHelperText: StateFlow<String?>
        get() = _serviceNameHelperText
    var serviceName: String = emptyString()

    private val _serviceCategoryHelperText = MutableStateFlow<String?>(null)
    val serviceCategoryHelperText: StateFlow<String?>
        get() = _serviceCategoryHelperText
    var serviceCategory: String = emptyString()

    private val _serviceDateHelperText = MutableStateFlow<String?>(null)
    val serviceDateHelperText: StateFlow<String?>
        get() = _serviceDateHelperText
    var serviceDate: String = emptyString()

    private val _serviceTypeHelperText = MutableStateFlow<String?>(null)
    val serviceTypesHelperText: StateFlow<String?>
        get() = _serviceTypeHelperText
    var serviceType: String = emptyString()

    private val _servicePriceHelperText = MutableStateFlow<String?>(null)
    val servicePriceHelperText: StateFlow<String?>
        get() = _servicePriceHelperText
    var servicePrice: String = emptyString()

    private val _serviceRememberHelperText = MutableStateFlow<String?>(null)
    val serviceRememberHelperText: StateFlow<String?>
        get() = _serviceRememberHelperText
    var serviceRemember: String = emptyString()

    init {
        getServices()
    }

    private fun Service.getDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        return LocalDate.parse(this.date, formatter)
    }

    private fun Service.updateDate() {
        val today = LocalDate.now()
        if (this.getDate().isEqual(today)) {
            when (this.type) {
                PaymentType.WEEKLY.type -> this.date = this.getDate().plus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                PaymentType.MONTHLY.type -> this.date = this.getDate().plus(1, ChronoUnit.MONTHS).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                PaymentType.YEARLY.type -> this.date = this.getDate().plus(1, ChronoUnit.YEARS).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
            }
            updateService(this.id, this)
        }
    }

    fun getServices() {
        viewModelScope.launch {
            servicesUseCase.getServices().collect { services ->
                services.forEach { service ->
                    service.updateDate()
                    alarmScheduler.scheduleAlarm(service)
                }
                _services.value = services.sortedBy { it.getDate() }
            }
        }
    }

    fun createService(service: Service) {
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

    fun updateService(serviceId: String, newServiceData: Service) {
        viewModelScope.launch {
            servicesUseCase.updateService(serviceId, newServiceData)
        }
    }

    fun deleteService(serviceId: String) {
        viewModelScope.launch {
            servicesUseCase.deleteService(serviceId)
        }
    }

    fun validateServiceName(): Boolean {
        return if (serviceName.isEmpty()) {
            _serviceNameHelperText.value = R.string.invalid_service_name.toString()
            false
        } else {
            _serviceNameHelperText.value = null
            true
        }
    }

    fun validateServiceCategory(): Boolean {
        return if (serviceCategory.isEmpty()) {
            _serviceCategoryHelperText.value = R.string.invalid_service_category.toString()
            false
        } else {
            _serviceCategoryHelperText.value = null
            true
        }
    }

    fun validateServiceDate(): Boolean {
        if (serviceDate.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
            val selectedDate = LocalDate.parse(serviceDate, formatter)
            val currentDate = LocalDate.now()

            if (selectedDate.isBefore(currentDate)) {
                _serviceDateHelperText.value = R.string.invalid_service_date.toString()
                return false
            }
        } else {
            _serviceDateHelperText.value = R.string.invalid_service_date.toString()
            return false
        }

        _serviceDateHelperText.value = null
        return true
    }

    fun validateServiceType() : Boolean {
        return if (serviceType.isEmpty()) {
            _serviceTypeHelperText.value = R.string.invalid_service_type.toString()
            false
        } else {
            _serviceTypeHelperText.value = null
            true
        }
    }

    fun validateServicePrice(): Boolean {
        return if (servicePrice.isEmpty() || servicePrice.contains(Regex("[ -,]"))) {
            _servicePriceHelperText.value = R.string.invalid_service_price.toString()
            false
        } else {
            _servicePriceHelperText.value = null
            true
        }
    }

    fun validateServiceRemember(): Boolean {
        return if (serviceRemember.isEmpty()) {
            _serviceRememberHelperText.value = R.string.invalid_service_remember.toString()
            false
        } else {
            _serviceRememberHelperText.value = null
            true
        }
    }
}