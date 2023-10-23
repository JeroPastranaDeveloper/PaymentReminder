package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8
import java.util.Calendar

@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    var selectedService by remember { mutableStateOf<Service?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        viewModel.services.value.map { service ->
            ServiceCard(service) {
                selectedService = service
            }
        }
        Spacer(modifier = Modifier.height(dimen56))
    }

    selectedService?.let {
        ServiceBottomSheet(service = it, viewModel) {
            selectedService = null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ServiceBottomSheet(service: Service?, viewModel: HomeViewModel, onDismiss: () -> Unit) {
    var serviceName by remember { mutableStateOf(TextFieldValue(service?.name ?: emptyString())) }

    var selectedCategory by remember { mutableStateOf(service?.category ?: emptyString()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)
    var categoriesExpanded by remember { mutableStateOf(false) }

    var selectedPaymentType by remember { mutableStateOf(service?.type ?: emptyString())}
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)
    var typesExpanded by remember { mutableStateOf(false) }

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
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.YEAR)
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
                        .weight(0.5f)
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
                Spacer(modifier = Modifier.height(dimen56))
            }
        },
        sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.HalfExpanded),
        content = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceCard(service: Service, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = emptyString(),
                modifier = Modifier
                    .width(dimen100)
                    .height(dimen100)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .padding(start = spacing4)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                /*if (service.name.length > 20) {
                    MarqueeText(
                        text = service.name,
                        modifier = Modifier.padding(spacing4)
                    )
                } else {
                    Text(
                        text = service.name,
                        modifier = Modifier.padding(spacing4),
                        style = MaterialTheme.typography.titleMedium
                    )
                }*/
                Text(
                    text = service.name,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = service.date,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = "${service.price}${Constants.EURO}",
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }

    /*@Composable
    @Stable
    private fun CategoriesDropDown(
        modifier: Modifier = Modifier,
        value: Categories,
        items: List<Categories>,
        onItemChanged: (Categories) -> Unit
    ) {
        val list by remember { derivedStateOf { items } }
        val selectedCategory = value.category
        val rotationState = remember { mutableFloatStateOf(0f) }
        SimpleDropDownSelection(
            startValue = selectedCategory.takeIf { it.isNotEmpty() }.orElse {
                //stringResource(id = R.string.select_category)
                 "Selecciona categoría"
            },
            list = list.map { it.category },
            modifier = modifier.padding(horizontal = spacing30)
        ) { position ->
            onItemChanged(list[position])
            rotationState.floatValue += 180f
        }
    }*/
}