package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen0
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20
import com.pr.paymentreminder.ui.theme.spacing8
import kotlinx.coroutines.flow.filter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ServiceBottomSheet(service: Service?, viewModel: HomeViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current

    var imageUrl by remember { mutableStateOf(TextFieldValue(service?.image ?: emptyString())) }

    /*val imageUriString = service?.image
    val imageUri = remember { mutableStateOf(imageUriString?.let { Uri.parse(it) }) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }*/

    var serviceName by remember { mutableStateOf(TextFieldValue(service?.name ?: emptyString())) }
    val serviceNameHelperText by viewModel.serviceNameHelperText.observeAsState()
    val wasServiceNameFieldFocused = remember { mutableStateOf(false) }

    var servicePrice by remember { mutableStateOf(TextFieldValue(service?.price ?: emptyString())) }
    val servicePriceHelperText by viewModel.servicePriceHelperText.observeAsState()
    val wasServicePriceFieldFocused = remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf(service?.category ?: emptyString()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)
    var categoriesExpanded by remember { mutableStateOf(false) }
    var categoriesValidation by remember { mutableStateOf(false) }
    val serviceCategoriesHelperText by viewModel.serviceCategoryHelperText.observeAsState()

    var serviceDate by remember { mutableStateOf(service?.date ?: emptyString()) }
    val serviceDateHelperText by viewModel.serviceDateHelperText.observeAsState()

    var selectedPaymentType by remember { mutableStateOf(service?.type ?: emptyString()) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)
    var typesExpanded by remember { mutableStateOf(false) }
    var typesValidation by remember { mutableStateOf(false) }
    val serviceTypesHelperText by viewModel.serviceTypesHelperText.observeAsState()

    var selectedRemember by remember { mutableStateOf(service?.remember ?: emptyString()) }
    val daysRemember = listOf(1, 2, 3)
    var daysExpanded by remember { mutableStateOf(false) }
    var rememberValidation by remember { mutableStateOf(false) }
    val serviceRememberHelperText by viewModel.serviceRememberHelperText.observeAsState()

    // TODO: ARREGLAR VISIBILIDAD DE LOS COJONES
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isVisible }
            .filter { isVisible -> !isVisible }
            .collect { onDismiss() }
    }

    ModalBottomSheetLayout(
        sheetShape = MaterialTheme.shapes.medium,
        sheetContent = {
            Column(
                modifier = Modifier.padding(dimen16)
            ) {
                //ImageBox(launcher, imageUri, Modifier.align(Alignment.CenterHorizontally))

                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text(stringResource(id = R.string.service_image_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(dimen16))

                TextField(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    label = { Text(stringResource(id = R.string.service_name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                        .onFocusChanged {
                            checkServiceNameFocus(
                                wasServiceNameFieldFocused,
                                it,
                                viewModel,
                                serviceName
                            )
                            wasServiceNameFieldFocused.value = it.isFocused
                        },
                    isError = !serviceNameHelperText.isNullOrEmpty(),
                    singleLine = true
                )

                HelperSeparator(serviceNameHelperText.orElse { emptyString() })
                ServiceNameHelperText(serviceNameHelperText)
                ServiceSeparator(serviceNameHelperText.orElse { emptyString() })

                Text(stringResource(id = R.string.category, selectedCategory),
                    modifier = Modifier.clickable { categoriesExpanded = !categoriesExpanded }
                )

                if (categoriesExpanded) {
                    viewModel.validateServiceCategory(selectedCategory)
                }

                DropdownMenu(
                    expanded = categoriesExpanded,
                    onDismissRequest = {
                        categoriesExpanded = false
                        categoriesValidation = true
                    }
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


                HelperSeparator(serviceCategoriesHelperText.orElse { emptyString() })
                ServiceCategoriesHelperText(serviceCategoriesHelperText, categoriesValidation)
                ServiceSeparator(serviceCategoriesHelperText.orElse { emptyString() })

                val datePickerDialog = remember {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, dayOfMonth)
                            val dateFormat =
                                SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
                            serviceDate = dateFormat.format(calendar.time)
                            viewModel.validateServiceDate(serviceDate)
                        },
                        Calendar.getInstance()[Calendar.YEAR],
                        Calendar.getInstance()[Calendar.MONTH],
                        Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                    )
                }

                Text(
                    text = stringResource(id = R.string.payment_date, serviceDate),
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )

                HelperSeparator(serviceDateHelperText.orElse { emptyString() })
                ServiceDateHelperText(serviceDateHelperText)
                ServiceSeparator(serviceDateHelperText.orElse { emptyString() })

                Text(stringResource(id = R.string.payment_type, selectedPaymentType),
                    modifier = Modifier
                        .clickable {
                            typesExpanded = !typesExpanded
                        }
                )

                if (typesExpanded) {
                    viewModel.validateServiceType(selectedPaymentType)
                }

                DropdownMenu(
                    expanded = typesExpanded,
                    onDismissRequest = {
                        typesExpanded = false
                        typesValidation = true
                    }
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
                viewModel.validateServiceType(selectedPaymentType)


                HelperSeparator(serviceTypesHelperText.orElse { emptyString() })
                ServiceTypesHelperText(serviceTypesHelperText, typesValidation)
                ServiceSeparator(serviceTypesHelperText.orElse { emptyString() })

                TextField(
                    value = servicePrice,
                    onValueChange = { servicePrice = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(stringResource(id = R.string.service_price)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                        .onFocusChanged {
                            checkServicePriceFocus(
                                wasServicePriceFieldFocused,
                                it,
                                viewModel,
                                servicePrice
                            )
                            wasServicePriceFieldFocused.value = it.isFocused
                        },
                    isError = !servicePriceHelperText.isNullOrEmpty(),
                    singleLine = true
                )

                HelperSeparator(servicePriceHelperText.orElse { emptyString() })
                ServicePriceHelperText(servicePriceHelperText)
                ServiceSeparator(servicePriceHelperText.orElse { emptyString() })

                Text(stringResource(id = R.string.remember_days_before, selectedRemember),
                    modifier = Modifier
                        .clickable {
                            daysExpanded = !daysExpanded
                        }
                )

                if (daysExpanded) {
                    viewModel.validateServiceRemember(selectedRemember)
                }

                DropdownMenu(
                    expanded = daysExpanded,
                    onDismissRequest = {
                        daysExpanded = false
                        rememberValidation = false
                    }
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
                viewModel.validateServiceRemember(selectedRemember)

                HelperSeparator(serviceRememberHelperText.orElse { emptyString() })
                ServiceRememberHelperText(serviceRememberHelperText, rememberValidation)
                ServiceSeparator(serviceRememberHelperText.orElse { emptyString() })

                val serviceId = service?.id
                SaveButton(
                    viewModel,
                    buttonFunctionality = ButtonFunctionality(
                        serviceName = serviceName,
                        selectedCategory = selectedCategory,
                        serviceDate = serviceDate,
                        selectedPaymentType = selectedPaymentType,
                        servicePrice = servicePrice,
                        serviceId = serviceId,
                        selectedRemember = selectedRemember,
                        imageUri = imageUrl,
                        service = service,
                        onDismiss = onDismiss,
                        context = context
                    )
                )

                Spacer(modifier = Modifier.height(dimen56))
            }
        },
        sheetState = sheetState,
        content = {}
    )
}

private fun checkServicePriceFocus(
    wasServicePriceFieldFocused: MutableState<Boolean>,
    it: FocusState,
    viewModel: HomeViewModel,
    servicePrice: TextFieldValue
) {
    if (wasServicePriceFieldFocused.value && !it.isFocused) {
        viewModel.validateServicePrice(servicePrice.text)
    }
}

private fun checkServiceNameFocus(
    wasServiceNameFieldFocused: MutableState<Boolean>,
    it: FocusState,
    viewModel: HomeViewModel,
    serviceName: TextFieldValue
) {
    if (wasServiceNameFieldFocused.value && !it.isFocused) {
        viewModel.validateServiceName(serviceName.text)
    }
}

@Composable
private fun ServiceRememberHelperText(
    serviceRememberHelperText: String?,
    rememberValidation: Boolean
) {
    if (!serviceRememberHelperText.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.invalid_service_remember),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
        !rememberValidation
    }
}

@Composable
private fun ServicePriceHelperText(
    servicePriceHelperText: String?
) {
    if (!servicePriceHelperText.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.invalid_service_price),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
    }
}

@Composable
private fun ServiceTypesHelperText(
    serviceTypesHelperText: String?,
    typesValidation: Boolean
) {
    if (!serviceTypesHelperText.isNullOrEmpty() && typesValidation) {
        Text(
            text = stringResource(id = R.string.invalid_service_type),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
        !typesValidation
    }
}

@Composable
private fun ServiceDateHelperText(
    serviceDateHelperText: String?
) {
    if (!serviceDateHelperText.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.invalid_service_date),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
    }
}

@Composable
private fun ServiceCategoriesHelperText(
    serviceCategoriesHelperText: String?,
    categoriesValidation: Boolean
) {
    if (!serviceCategoriesHelperText.isNullOrEmpty() && categoriesValidation) {
        Text(
            text = stringResource(id = R.string.invalid_service_category),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
        !categoriesValidation
    }
}

@Composable
private fun ServiceNameHelperText(
    serviceNameHelperText: String?
) {
    if (!serviceNameHelperText.isNullOrEmpty()) {
        Text(
            text = stringResource(id = R.string.invalid_service_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing20, end = spacing16, bottom = spacing8),
            color = Color.Red
        )
    }
}

/*@Composable
private fun ImageBox(
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    imageUri: MutableState<Uri?>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .size(dimen150)
        .clickable {
            launcher.launch(Constants.IMAGE_PATH)
        }) {
        if (imageUri.value != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri.value),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}*/

data class ButtonFunctionality(
    val serviceName: TextFieldValue,
    val selectedCategory: String,
    val serviceDate: String,
    val selectedPaymentType: String,
    val servicePrice: TextFieldValue,
    val serviceId: String?,
    val selectedRemember: String,
    val imageUri: TextFieldValue,
    val service: Service?,
    val onDismiss: () -> Unit,
    val context: Context
)

@Composable
private fun SaveButton(
    viewModel: HomeViewModel,
    buttonFunctionality: ButtonFunctionality
) {
    Button(
        onClick = {
            with(buttonFunctionality) {
                with(viewModel) {
                    val isServiceNameValid = validateServiceName(serviceName.text)
                    val isServiceCategoryValid = validateServiceCategory(selectedCategory)
                    val isServiceDateValid = validateServiceDate(serviceDate)
                    val isServiceTypeValid = validateServiceType(selectedPaymentType)
                    val isServicePriceValid = validateServicePrice(servicePrice.text)

                    if (isServiceNameValid && isServiceCategoryValid && isServiceDateValid && isServiceTypeValid && isServicePriceValid) {
                        val serviceData = Service(
                            id = serviceId.orElse { emptyString() },
                            category = selectedCategory,
                            name = serviceName.text,
                            color = emptyString(),
                            date = serviceDate,
                            price = servicePrice.text,
                            remember = selectedRemember,
                            type = selectedPaymentType,
                            image = imageUri.text
                        )

                        if (service != null) {
                            updateService(serviceData, viewModel)
                        } else {
                            createService(serviceData, viewModel)
                        }
                        viewModel.getServices()
                        onDismiss()

                    } else {
                        validateServiceName(serviceName.text)
                        validateServiceCategory(selectedCategory)
                        validateServiceDate(serviceDate)
                        validateServiceType(selectedPaymentType)
                        validateServicePrice(servicePrice.text)
                        validateServiceRemember(selectedRemember)

                        Toast.makeText(
                            context,
                            R.string.invalid_data,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.btn_save))
    }
}

@Composable
private fun HelperSeparator(helperText: String) {
    Spacer(modifier = Modifier.height(if (helperText.isEmpty()) dimen16 else dimen8))
}

@Composable
fun ServiceSeparator(helperText: String) {
    Spacer(modifier = Modifier.height(if (helperText.isEmpty()) dimen0 else dimen8))
}

private fun updateService(
    service: Service,
    viewModel: HomeViewModel
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
        image = service.image
    )
    viewModel.updateService(
        service.id.orElse { emptyString() },
        updatedServiceData
    )
}

private fun createService(
    serviceData: Service,
    viewModel: HomeViewModel
) {
    val newService = Service(
        id = emptyString(),
        category = serviceData.category,
        color = emptyString(),
        date = serviceData.date,
        name = serviceData.name,
        price = serviceData.price,
        remember = serviceData.remember,
        type = serviceData.type,
        image = serviceData.image.toString()
    )
    viewModel.createService(newService)
}