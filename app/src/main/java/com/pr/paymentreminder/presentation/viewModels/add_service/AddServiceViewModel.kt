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
        when(intent) {
            is UiIntent.CreateService -> createService(intent.service)
            is UiIntent.UpdateService -> updateService(intent.serviceId, intent.service)
            is UiIntent.ValidateService -> validateServiceItem(intent.item, intent.value)
            is UiIntent.CheckIntent -> checkIntent(intent.serviceId, intent.action)
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

        if(action == ButtonActions.EDIT.name) getService(serviceId)
    }

    private suspend fun getService(serviceId: String) {
        servicesUseCase.getService(serviceId).collect { service ->
            setState {
                copy(
                    service = service,
                    serviceTextField = service.toServiceTextField(),
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