package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.emptyString
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ServiceBottomSheet(service: Service?, viewModel: HomeViewModel, onDismiss: () -> Unit) {
    var serviceName by remember { mutableStateOf(TextFieldValue(service?.name ?: emptyString())) }
    var servicePrice by remember { mutableStateOf(TextFieldValue(service?.price ?: emptyString())) }

    var selectedCategory by remember { mutableStateOf(service?.category ?: emptyString()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)
    var categoriesExpanded by remember { mutableStateOf(false) }

    var selectedPaymentType by remember { mutableStateOf(service?.type ?: emptyString()) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)
    var typesExpanded by remember { mutableStateOf(false) }

    var selectedRemember by remember { mutableStateOf(service?.remember ?: emptyString()) }
    val daysRemember = listOf(1, 2, 3)
    var daysExpanded by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier.padding(dimen16)
            ) {
                TextField(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    label = { Text(stringResource(id = R.string.service_name)) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(dimen16))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(id = R.string.category, selectedCategory),
                        modifier = Modifier
                            .clickable {
                                categoriesExpanded = !categoriesExpanded
                            }
                            .weight(0.5f)
                    )
                    if (categoriesExpanded) {
                        DropdownMenu(
                            expanded = categoriesExpanded,
                            onDismissRequest = { categoriesExpanded = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(onClick = {
                                    selectedCategory = category.category
                                    categoriesExpanded = false
                                }) {
                                    Text(text = category.category)
                                }
                            }
                        }
                    }

                    val context = LocalContext.current
                    viewModel.checkDateText(service)
                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                // TODO: COMPROBAR EL VIEWMODEL Y ETC
                                viewModel.updateDate("${dayOfMonth}/${month + 1}/${year}")
                            },
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        )
                    }
                    Text(
                        text = viewModel.textDate.value,
                        modifier = Modifier
                            .clickable {
                                datePickerDialog.show()
                            }
                            .weight(0.5f)
                    )
                }

                Spacer(modifier = Modifier.height(dimen16))

                Text(
                    stringResource(id = R.string.payment_type, selectedPaymentType),
                    modifier = Modifier
                        .clickable {
                            typesExpanded = !typesExpanded
                        }
                )
                if (typesExpanded) {
                    DropdownMenu(
                        expanded = typesExpanded,
                        onDismissRequest = { typesExpanded = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        types.forEach { type ->
                            DropdownMenuItem(onClick = {
                                selectedPaymentType = type.type
                                typesExpanded = false
                            }) {
                                Text(text = type.type)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimen16))

                TextField(
                    value = servicePrice,
                    onValueChange = { servicePrice = it },
                    label = { Text(stringResource(id = R.string.service_price)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dimen16))

                Text(
                    stringResource(id = R.string.remember_days_before, selectedRemember),
                    modifier = Modifier
                        .clickable {
                            daysExpanded = !daysExpanded
                        }
                )
                if (daysExpanded) {
                    DropdownMenu(
                        expanded = daysExpanded,
                        onDismissRequest = { daysExpanded = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        daysRemember.forEach { day ->
                            DropdownMenuItem(onClick = {
                                selectedRemember = day.toString()
                                daysExpanded = false
                            }) {
                                Text(text = day.toString())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimen16))

                Button(
                    onClick = {
                        // TODO
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.btn_save))
                }

                Spacer(modifier = Modifier.height(dimen56))
            }
        },
        sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.HalfExpanded),
        content = {}
    )
}