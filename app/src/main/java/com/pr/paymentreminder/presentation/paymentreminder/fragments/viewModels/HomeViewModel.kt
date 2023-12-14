package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    private val _serviceNameHelperText = MutableLiveData<String?>()
    val serviceNameHelperText: LiveData<String?>
        get() = _serviceNameHelperText

    private val _serviceCategoryHelperText = MutableLiveData<String?>()
    val serviceCategoryHelperText: LiveData<String?>
        get() = _serviceCategoryHelperText

    private val _serviceDateHelperText = MutableLiveData<String?>()
    val serviceDateHelperText: LiveData<String?>
        get() = _serviceDateHelperText

    private val _serviceTypeHelperText = MutableLiveData<String?>()
    val serviceTypesHelperText: LiveData<String?>
        get() = _serviceTypeHelperText

    private val _servicePriceHelperText = MutableLiveData<String?>()
    val servicePriceHelperText: LiveData<String?>
        get() = _servicePriceHelperText

    private val _serviceRememberHelperText = MutableLiveData<String?>()
    val serviceRememberHelperText: LiveData<String?>
        get() = _serviceRememberHelperText

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

    fun validateServiceName(serviceName: String): Boolean {
        val isValid: Boolean
        if (serviceName.isEmpty()) {
            _serviceNameHelperText.value = R.string.invalid_service_name.toString()
            isValid = false
        } else {
            _serviceNameHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validateServiceCategory(serviceCategory: String): Boolean {
        val isValid: Boolean
        if (serviceCategory.isEmpty()) {
            _serviceCategoryHelperText.value = R.string.invalid_service_category.toString()
            isValid = false
        } else {
            _serviceCategoryHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validateServiceDate(serviceDate: String): Boolean {
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

    fun validateServiceType(serviceType: String) : Boolean {
        val isValid: Boolean
        if (serviceType.isEmpty()) {
            _serviceTypeHelperText.value = R.string.invalid_service_type.toString()
            isValid = false
        } else {
            _serviceTypeHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validateServicePrice(servicePrice: String): Boolean {
        val isValid: Boolean
        if (servicePrice.isEmpty() || servicePrice.contains(Regex("[ -,]"))) {
            _servicePriceHelperText.value = R.string.invalid_service_price.toString()
            isValid = false
        } else {
            _servicePriceHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validateServiceRemember(serviceRemember: String): Boolean {
        val isValid: Boolean
        if (serviceRemember.isEmpty()) {
            _serviceRememberHelperText.value = R.string.invalid_service_remember.toString()
            isValid = false
        } else {
            _serviceRememberHelperText.value = null
            isValid = true
        }
        return isValid
    }
}