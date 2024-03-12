package com.pr.paymentreminder.presentation.paymentreminder.add_service

import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.categoryItem
import com.pr.paymentreminder.data.model.dateItem
import com.pr.paymentreminder.data.model.nameItem
import com.pr.paymentreminder.data.model.priceItem
import com.pr.paymentreminder.data.model.rememberItem
import com.pr.paymentreminder.data.model.typeItem
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.emptyString

fun initialValidations(
    viewModel: AddServiceViewModel,
    serviceName: TextFieldValue,
    servicePrice: TextFieldValue,
    selectedCategory: String,
    serviceDate: String,
    selectedType: String,
    selectedRemember: String
) {
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(nameItem, serviceName.text))
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(priceItem, servicePrice.text))
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(categoryItem, selectedCategory))
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(dateItem, serviceDate))
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(typeItem, selectedType))
    viewModel.sendIntent(AddServiceViewContract.UiIntent.ValidateService(rememberItem, selectedRemember))
}

fun updateService(
    service: Service,
    viewModel: AddServiceViewModel
) {
    val updatedServiceData = Service(
        id = service.id,
        category = service.category,
        color = emptyString(),
        date = service.date,
        name = service.name,
        price = service.price,
        remember = service.remember,
        type = service.type,
        image = service.image,
        url = service.url
    )
    viewModel.sendIntent(
        AddServiceViewContract.UiIntent.UpdateService(
            service.id,
            updatedServiceData
        )
    )
}

fun createService(
    serviceData: Service,
    viewModel: AddServiceViewModel
) {
    viewModel.sendIntent(AddServiceViewContract.UiIntent.CreateService(serviceData.copy(id = emptyString())))
}