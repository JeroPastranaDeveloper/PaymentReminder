package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.SaveButtonFunctionality
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.createService
import com.pr.paymentreminder.presentation.paymentreminder.add_service.initialValidations
import com.pr.paymentreminder.presentation.paymentreminder.add_service.updateService
import com.pr.paymentreminder.presentation.paymentreminder.fragments.ButtonActions
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse

@Composable
fun SaveButton(
    viewModel: AddServiceViewModel,
    saveButtonFunctionality: SaveButtonFunctionality
) {
    Button(
        onClick = {
            with(saveButtonFunctionality) {
                initialValidations(
                    viewModel,
                    serviceName,
                    servicePrice,
                    selectedCategory,
                    serviceDate,
                    selectedPaymentType,
                    selectedRemember
                )
                with(viewModel) {
                    val isServiceNameValid = !state.value.serviceNameHelperText
                    val isServiceCategoryValid = !state.value.serviceCategoryHelperText
                    val isServiceDateValid = !state.value.serviceDateHelperText
                    val isServiceTypeValid = !state.value.serviceTypeHelperText
                    val isServicePriceValid = !state.value.servicePriceHelperText
                    val isServiceRememberValid = !state.value.serviceRememberHelperText

                    if (isServiceNameValid && isServiceCategoryValid && isServiceDateValid && isServiceTypeValid && isServicePriceValid && isServiceRememberValid) {
                        val serviceData = Service(
                            id = serviceId.orElse { emptyString() },
                            category = selectedCategory,
                            name = serviceName.text,
                            color = emptyString(),
                            date = serviceDate,
                            price = servicePrice.text,
                            remember = selectedRemember,
                            type = selectedPaymentType,
                            comments = comments,
                            image = imageUri.text,
                            url = serviceUrl.text
                        )

                        when(action) {
                            ButtonActions.EDIT.name -> updateService(serviceData, viewModel)
                            ButtonActions.ADD.name -> createService(serviceData, viewModel)
                        }

                    } else {
                        initialValidations(
                            viewModel,
                            serviceName,
                            servicePrice,
                            selectedCategory,
                            serviceDate,
                            selectedPaymentType,
                            selectedRemember
                        )

                        Toast.makeText(context, R.string.invalid_data, Toast.LENGTH_LONG).show()
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.btn_save))
    }
}