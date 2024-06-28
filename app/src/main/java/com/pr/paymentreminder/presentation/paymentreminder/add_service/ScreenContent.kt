package com.pr.paymentreminder.presentation.paymentreminder.add_service

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.categoryItem
import com.pr.paymentreminder.data.model.dateItem
import com.pr.paymentreminder.data.model.rememberItem
import com.pr.paymentreminder.data.model.typeItem
import com.pr.paymentreminder.presentation.paymentreminder.compose.HelperText
import com.pr.paymentreminder.ui.theme.Visible
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orFalse
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServiceFactory(
    state: AddServiceViewContract.UiState,
    action: AddServiceActions,
    serviceId: String
) {
    val context = LocalContext.current

    var serviceName by remember { mutableStateOf(state.service.name) }
    var servicePrice by remember { mutableStateOf(state.service.price) }

    var selectedCategory by remember { mutableStateOf(state.service.category) }
    val categories = state.categories

    var serviceDate by remember { mutableStateOf(state.service.date) }

    var selectedType by remember { mutableStateOf(state.service.type) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)

    var selectedRemember by remember { mutableStateOf(state.service.remember) }

    var comments by remember { mutableStateOf(state.service.comments.orEmpty()) }
    var imageUrl by remember { mutableStateOf(state.service.image.orEmpty()) }
    var serviceUrl by remember { mutableStateOf(state.service.url.orEmpty()) }

    LaunchedEffect(key1 = state.service.name) {
        serviceName = state.service.name
        servicePrice = state.service.price
        selectedCategory = state.service.category
        serviceDate = state.service.date
        selectedType = state.service.type
        comments = state.service.comments.orEmpty()
        selectedRemember = state.service.remember
        imageUrl = state.service.image.orEmpty()
        serviceUrl = state.service.url.orEmpty()
    }

    Column {
        Surface {
            TopAppBar(
                title = { Text(stringResource(id = R.string.edit_service_title)) },
                navigationIcon = {
                    IconButton(onClick = { action.onBack }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(start = spacing16, end = spacing16, bottom = spacing16),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(
                        value = serviceName,
                        onValueChange = { serviceName = it },
                        label = { Text(stringResource(id = R.string.service_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing8),
                        singleLine = false
                    )

                    Visible(state.nameHelperText.orFalse()) {
                        HelperText(stringResource(R.string.invalid_service_name))
                    }

                    OutlinedTextField(
                        value = servicePrice,
                        onValueChange = { servicePrice = it },
                        label = { Text(stringResource(id = R.string.service_price)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing8),
                        singleLine = false
                    )

                    Visible(state.priceHelperText.orFalse()) {
                        HelperText(stringResource(R.string.invalid_service_price))
                    }

                    Spacer(modifier = Modifier.height(dimen16))

                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val calendar = Calendar.getInstance()
                                calendar.set(year, month, dayOfMonth)
                                val dateFormat =
                                    SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
                                serviceDate = dateFormat.format(calendar.time)
                            },
                            Calendar.getInstance()[Calendar.YEAR],
                            Calendar.getInstance()[Calendar.MONTH],
                            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                        ).apply {
                            setOnDismissListener {
                                action.onValidateService(dateItem, serviceDate)
                            }
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.payment_date, serviceDate),
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )

                    Visible(state.dateHelperText.orFalse()) {
                        Spacer(modifier = Modifier.height(dimen4))
                        HelperText(stringResource(id = R.string.invalid_service_date))
                    }

                    Spacer(modifier = Modifier.height(dimen16))

                    Column(modifier = Modifier.wrapContentSize()) {
                        var typesExpanded by remember { mutableStateOf(false) }

                        Text(
                            text = stringResource(id = R.string.payment_type, selectedType),
                            modifier = Modifier.clickable { typesExpanded = !typesExpanded }
                        )

                        Visible(state.typeHelperText.orFalse()) {
                            Spacer(modifier = Modifier.height(dimen4))
                            HelperText(stringResource(id = R.string.invalid_service_type))
                        }

                        DropdownMenu(
                            expanded = typesExpanded,
                            onDismissRequest = {
                                typesExpanded = false
                                action.onValidateService(typeItem, selectedType)
                            }
                        ) {
                            types.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(text = type.type) },
                                    onClick = {
                                        selectedType = type.type
                                        typesExpanded = false
                                        action.onValidateService(typeItem, selectedType)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(dimen16))
                    }

                    Visible(visible = state.action != ButtonActions.EDIT_PAID.name) {
                        Column(modifier = Modifier.wrapContentSize()) {
                            val rememberDays: List<Int> = listOf(1, 2, 3)
                            var daysExpanded by remember { mutableStateOf(false) }

                            Text(
                                text = stringResource(
                                    id = if (selectedRemember == rememberDays[0].toString()) R.string.remember_day_before else R.string.remember_days_before,
                                    selectedRemember
                                ),
                                modifier = Modifier.clickable { daysExpanded = !daysExpanded }
                            )

                            Visible(state.rememberHelperText.orFalse()) {
                                Spacer(modifier = Modifier.height(dimen4))
                                HelperText(stringResource(R.string.invalid_service_remember))
                            }

                            DropdownMenu(
                                expanded = daysExpanded,
                                onDismissRequest = {
                                    daysExpanded = false
                                    action.onValidateService(rememberItem, selectedRemember)
                                }
                            ) {
                                rememberDays.forEach { day ->
                                    DropdownMenuItem(
                                        text = { Text(text = day.toString()) },
                                        onClick = {
                                            selectedRemember = day.toString()
                                            daysExpanded = false
                                            action.onValidateService(rememberItem, selectedRemember)
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(dimen16))
                        }
                    }

                    Column(modifier = Modifier.wrapContentSize()) {
                        var categoryExpanded by remember { mutableStateOf(false) }

                        Text(
                            text = stringResource(id = R.string.category, selectedCategory),
                            modifier = Modifier.clickable {
                                categoryExpanded = !categoryExpanded
                            }
                        )

                        Visible(state.categoryHelperText.orFalse()) {
                            Spacer(modifier = Modifier.height(dimen4))
                            HelperText(stringResource(R.string.invalid_service_category))
                        }

                        DropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = {
                                categoryExpanded = false
                                action.onValidateService(categoryItem, selectedCategory)
                            }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category.name) },
                                    onClick = {
                                        selectedCategory = category.name
                                        categoryExpanded = false
                                        action.onValidateService(categoryItem, selectedCategory)
                                    }
                                )
                            }
                        }
                    }

                    Visible(visible = state.action != ButtonActions.EDIT_PAID.name) {
                        OutlinedTextField(
                            value = imageUrl,
                            onValueChange = { imageUrl = it },
                            label = { Text(stringResource(id = R.string.service_image_url)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing8),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = serviceUrl,
                            onValueChange = { serviceUrl = it },
                            label = { Text(stringResource(id = R.string.service_url)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing8),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = comments,
                        onValueChange = { comments = it },
                        label = { Text(stringResource(id = R.string.service_comments)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing8),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    val newServiceData = Service(
                        id = serviceId,
                        selectedCategory,
                        color = emptyString(),
                        serviceDate,
                        serviceName,
                        servicePrice,
                        selectedRemember,
                        selectedType,
                        false,
                        imageUrl,
                        comments,
                        serviceUrl
                    )

                    SaveButton(newServiceData, action)
                }
            }
        }
    }
}

@Composable
private fun SaveButton(
    service: Service,
    action: AddServiceActions
) {
    OutlinedButton(
        onClick = { action.onValidateAndSave(service) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.btn_save))
    }
}
