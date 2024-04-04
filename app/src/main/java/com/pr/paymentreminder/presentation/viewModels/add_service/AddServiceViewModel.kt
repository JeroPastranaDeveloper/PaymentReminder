package com.pr.paymentreminder.presentation.viewModels.add_service

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.presentation.paymentreminder.fragments.ButtonActions
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val servicesUseCase: ServicesUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.CheckIntent -> checkIntent(intent.serviceId, intent.action)
            is UiIntent.CreateService -> createService(intent.service)
            is UiIntent.UpdateService -> updateService(intent.serviceId, intent.service)
            is UiIntent.ValidateService -> validateServiceItem(intent.item, intent.value)
        }
    }

    private suspend fun checkIntent(serviceId: String, action: String) {
        setState {
            copy(
                isLoading = true
            )
        }

        setState {
            copy(
                action = action,
                serviceId = serviceId,
                isLoading = action != ButtonActions.ADD.name
            )
        }

        if (action == ButtonActions.EDIT.name) getService(serviceId)
    }

    private suspend fun getService(serviceId: String) {
        servicesUseCase.getService(serviceId).collect { service ->
            setState {
                copy(
                    service = service,
                    isLoading = false
                )
            }
        }
    }

    private fun createService(service: Service) {
        viewModelScope.launch {
            val database = Firebase.database
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val servicesRef = database.getReference("$userId/${Constants.SERVICES}")
            val id = servicesRef.push().key.orEmpty()
            service.id = id
            servicesUseCase.createService(id, service)
        }

        dispatchAction(UiAction.GoBack)
    }

    private fun updateService(serviceId: String, newServiceData: Service) {
        viewModelScope.launch {
            servicesUseCase.updateService(serviceId, newServiceData)
        }
        dispatchAction(UiAction.GoBack)
    }

    private fun validateServiceItem(item: ServiceItem, value: String) {
        val isEmpty = value.isEmpty()
        setState { copy(isLoading = true) }

        when(item) {
            ServiceItem.CATEGORY -> setState { copy(categoryHelperText = isEmpty) }
            ServiceItem.DATE -> setState { copy(dateHelperText = isEmpty) }
            ServiceItem.NAME -> setState { copy(nameHelperText = isEmpty) }
            ServiceItem.PRICE -> setState { copy(priceHelperText = isEmpty) }
            ServiceItem.REMEMBER -> setState { copy(rememberHelperText = isEmpty) }
            ServiceItem.TYPE -> setState { copy(typeHelperText = isEmpty) }
        }

        setState { copy(isLoading = false) }
    }
}
