package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceUseCase: ServiceUseCase
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    init {
        getServices()
    }

    private fun getServices() {
        viewModelScope.launch {
            _services.value = serviceUseCase.getServices()
        }
    }
}