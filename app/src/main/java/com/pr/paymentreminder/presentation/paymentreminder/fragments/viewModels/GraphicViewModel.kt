package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    private val _weeklyServices = mutableStateOf<List<Service>>(emptyList())
    val weeklyServices: State<List<Service>> = _weeklyServices

    private val _monthlyServices = mutableStateOf<List<Service>>(emptyList())
    val monthlyServices: State<List<Service>> = _monthlyServices

    private val _annualServices = mutableStateOf<List<Service>>(emptyList())
    val annualServices: State<List<Service>> = _annualServices

    init {
        getServices()
    }

    fun getServices() {
        viewModelScope.launch {
            _services.value = servicesUseCase.getServices()

            _weeklyServices.value = _services.value.filter { it.type == PaymentType.WEEKLY.type }
            _monthlyServices.value = _services.value.filter { it.type == PaymentType.MONTHLY.type }
            _annualServices.value = _services.value.filter { it.type == PaymentType.YEARLY.type }
        }
    }
}