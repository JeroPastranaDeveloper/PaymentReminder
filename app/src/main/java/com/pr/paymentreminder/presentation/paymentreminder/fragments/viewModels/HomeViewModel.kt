package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    private val _serviceNameHelperText = MutableLiveData<String?>()
    val serviceNameHelperText: LiveData<String?>
        get() = _serviceNameHelperText

    private val _servicePriceHelperText = MutableLiveData<String?>()
    val servicePriceHelperText: LiveData<String?>
        get() = _servicePriceHelperText

    private val _textDate = mutableStateOf(emptyString())
    val textDate: State<String> = _textDate

    init {
        getServices()
    }

    fun getServices() {
        viewModelScope.launch {
            _services.value = servicesUseCase.getServices()
        }
    }

    fun checkDateText(service: Service?) {
        if (service?.date.isNullOrEmpty()) {
            _textDate.value = R.string.payment_date.toString()
        } else {
            _textDate.value = service?.date.orElse { emptyString() }
        }
    }

    fun updateDate(newDate: String) {
        _textDate.value = newDate
    }

    fun createService(service: Service) {
        viewModelScope.launch {
            servicesUseCase.createService(service)
        }
    }

    fun updateService(serviceName: String, newServiceData: Service) {
        viewModelScope.launch {
            servicesUseCase.updateService(serviceName, newServiceData)
        }
    }

    fun deleteService(serviceName: String) {
        viewModelScope.launch {
            servicesUseCase.deleteService(serviceName)
        }
    }

    fun validateServiceName(serviceName: String) : Boolean {
        val isValid : Boolean
        if (serviceName.isEmpty()) {
            _serviceNameHelperText.value = R.string.invalid_service_name.toString()
            isValid = false
        } else {
            _serviceNameHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validateServicePrice(servicePrice: String) : Boolean {
        val isValid : Boolean
        if (servicePrice.isEmpty() || servicePrice.contains(Regex("[ -,]"))) {
            _servicePriceHelperText.value = R.string.invalid_service_price.toString()
            isValid = false
        } else {
            _servicePriceHelperText.value = null
            isValid = true
        }
        return isValid
    }
}