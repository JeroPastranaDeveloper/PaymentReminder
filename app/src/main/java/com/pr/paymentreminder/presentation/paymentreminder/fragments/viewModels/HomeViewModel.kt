package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : ViewModel() {
    private val _services = mutableStateOf<List<Service>>(emptyList())
    val services: State<List<Service>> = _services

    private val _textDate = mutableStateOf(emptyString())
    val textDate: State<String> = _textDate

    init {
        getServices()
    }

    private fun getServices() {
        viewModelScope.launch {
            _services.value = servicesUseCase.getServices()
        }
    }

    @Composable
    fun CheckDateText(service: Service?) {
        if (service?.date.isNullOrEmpty()) {
            _textDate.value = stringResource(id = R.string.payment_date)
        } else {
            _textDate.value = service?.date.toString()
        }
    }
}