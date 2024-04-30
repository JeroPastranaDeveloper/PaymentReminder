package com.pr.paymentreminder.presentation.paymentreminder.add_service

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.data.model.categoryItem
import com.pr.paymentreminder.data.model.dateItem
import com.pr.paymentreminder.data.model.nameItem
import com.pr.paymentreminder.data.model.priceItem
import com.pr.paymentreminder.data.model.rememberItem
import com.pr.paymentreminder.data.model.typeItem
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service_form.GetServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val getService: GetServiceUseCase,
    private val createService: CreateServiceUseCase,
    private val updateService: UpdateServiceUseCase,
    private val saveServiceForm: SaveServiceFormUseCase,
    private val getServiceForm: GetServiceFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.CheckIntent -> checkIntent(intent.serviceId, intent.action)
            is UiIntent.ValidateAndSave -> validateAndSave(intent.service)
            is UiIntent.ValidateService -> validateServiceItem(intent.item, intent.value, state.value.service)
        }
    }

    private fun validateAndSave(service: Service) {
        if (state.value.action != ButtonActions.EDIT_PAID.name) {

            validateServiceItem(nameItem, service.name)
            validateServiceItem(priceItem, service.price)
            validateServiceItem(categoryItem, service.category)
            validateServiceItem(dateItem, service.date)
            validateServiceItem(typeItem, service.type)
            validateServiceItem(rememberItem, service.remember)

            if (!state.value.categoryHelperText && !state.value.dateHelperText && !state.value.nameHelperText && !state.value.priceHelperText && !state.value.rememberHelperText && !state.value.typeHelperText) {
                when (state.value.action) {
                    ButtonActions.EDIT.name -> updateService(service)
                    else -> createService(service)
                }
            }
        } else updatePaidService(service)
    }

    private fun updatePaidService(service: Service) {
        validateServiceItem(nameItem, service.name, service)
        validateServiceItem(priceItem, service.price, service)
        validateServiceItem(categoryItem, service.category, service)
        validateServiceItem(dateItem, service.date, service)
        validateServiceItem(typeItem, service.type, service)

        if (!state.value.categoryHelperText && !state.value.dateHelperText && !state.value.nameHelperText && !state.value.priceHelperText && !state.value.typeHelperText) {
            viewModelScope.launch {
                saveServiceForm(service)
            }

            SharedShowSnackBarType.updateSharedSnackBarType(CustomSnackBarType.UPDATE_PAID)
            dispatchAction(UiAction.GoBack)
        }
    }

    private fun checkIntent(serviceId: String, action: String) {
        setState {
            copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val service = when(action) {
                ButtonActions.EDIT.name -> getService(serviceId).firstOrNull()
                ButtonActions.EDIT_PAID.name -> getServiceForm(serviceId)
                else -> null
            }

            setState {
                copy(
                    action = action,
                    serviceId = serviceId,
                    service = service ?: Service(),
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
            createService(id, service)
        }

        SharedShowSnackBarType.updateSharedSnackBarType(CustomSnackBarType.CREATE)
        dispatchAction(UiAction.GoBack)
    }

    private fun updateService(newServiceData: Service) {
        viewModelScope.launch {
            updateService(newServiceData.id, newServiceData)
        }
        SharedShowSnackBarType.updateSharedSnackBarType(CustomSnackBarType.UPDATE)
        dispatchAction(UiAction.GoBack)
    }

    private fun Service.getDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        return LocalDate.parse(this.date, formatter)
    }

    private fun validateServiceItem(
        item: ServiceItem,
        value: String,
        service: Service = Service()
    ) {
        val isEmpty = value.isEmpty()
        val today = LocalDate.now()
        val isDateAfter = if(state.value.action == ButtonActions.EDIT_PAID.name) {
            service.getDate().isAfter(today)
        } else false

        setState { copy(isLoading = true) }

        when (item) {
            ServiceItem.CATEGORY -> setState { copy(categoryHelperText = isEmpty) }
            ServiceItem.DATE -> setState { copy(dateHelperText = if (state.value.action == ButtonActions.EDIT_PAID.name) isDateAfter else isEmpty) }
            ServiceItem.NAME -> setState { copy(nameHelperText = isEmpty) }
            ServiceItem.PRICE -> setState { copy(priceHelperText = isEmpty) }
            ServiceItem.TYPE -> setState { copy(typeHelperText = isEmpty) }
            ServiceItem.REMEMBER -> if (state.value.action != ButtonActions.EDIT_PAID.name) setState { copy(rememberHelperText = isEmpty) }
        }

        setState { copy(isLoading = false) }
    }
}
