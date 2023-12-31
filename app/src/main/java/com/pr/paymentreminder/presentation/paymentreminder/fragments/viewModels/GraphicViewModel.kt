package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphicViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : ViewModel() {
    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services

    private val _filteredServices = MutableStateFlow<List<Service>>(emptyList())
    val filteredServices: StateFlow<List<Service>> = _filteredServices

    init {
        getServices()
    }

    private fun getServices() {
        viewModelScope.launch {
            servicesUseCase.getServices().collect { services ->
                _services.value = services
                _filteredServices.value = services
            }
        }
    }

    fun filterServices(filter: String) {
        viewModelScope.launch {
            servicesUseCase.getFilteredServices(filter).collect { filteredServices ->
                _filteredServices.value = filteredServices
            }
        }
    }
}