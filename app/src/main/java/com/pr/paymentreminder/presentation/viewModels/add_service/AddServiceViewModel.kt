package com.pr.paymentreminder.presentation.viewModels.add_service

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.data.model.categoryItem
import com.pr.paymentreminder.data.model.dateItem
import com.pr.paymentreminder.data.model.nameItem
import com.pr.paymentreminder.data.model.priceItem
import com.pr.paymentreminder.data.model.rememberItem
import com.pr.paymentreminder.data.model.typeItem
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
            is UiIntent.ValidateAndSave -> validateAndSave(intent.service)
            is UiIntent.ValidateService -> validateServiceItem(intent.item, intent.value)
        }
    }

    private fun validateAndSave(service: Service) {
        validateServiceItem(nameItem, service.name)
        validateServiceItem(priceItem, service.price)
        validateServiceItem(categoryItem, service.category)
        validateServiceItem(dateItem, service.date)
        validateServiceItem(typeItem, service.type)
        validateServiceItem(rememberItem, service.remember)

        if (!state.value.categoryHelperText && !state.value.dateHelperText && !state.value.nameHelperText && !state.value.priceHelperText && !state.value.rememberHelperText && !state.value.typeHelperText) {
            when (state.value.action) {
                ButtonActions.EDIT.name -> updateService(
                    service.id, Service(
                        category = service.category,
                        price = service.price,
                        name = service.name,
                        date = service.date,
                        type = service.type,
                        remember = service.remember,
                        image = service.image,
                        comments = service.comments,
                        url = service.url
                    )
                )

                else -> createService(
                    Service(
                        category = service.category,
                        price = service.price,
                        name = service.name,
                        date = service.date,
                        type = service.type,
                        remember = service.remember,
                        image = service.image,
                        comments = service.comments,
                        url = service.url
                    )
                )
            }
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

        when (item) {
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
