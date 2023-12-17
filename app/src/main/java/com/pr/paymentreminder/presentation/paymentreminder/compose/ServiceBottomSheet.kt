package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen0
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen2
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

@OptIn(ExperimentalMaterialApi::class)
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
    val serviceNameHelperText by viewModel.serviceNameHelperText.collectAsState()
    val wasServiceNameFieldFocused = remember { mutableStateOf(false) }

    var servicePrice by remember { mutableStateOf(TextFieldValue(service?.price ?: emptyString())) }
    val servicePriceHelperText by viewModel.servicePriceHelperText.collectAsState()
    val wasServicePriceFieldFocused = remember { mutableStateOf(false) }

    val selectedCategory by remember { mutableStateOf(service?.category ?: emptyString()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)
    var categoriesExpanded by remember { mutableStateOf(false) }
    val categoriesValidation by remember { mutableStateOf(false) }
    val serviceCategoriesHelperText by viewModel.serviceCategoryHelperText.collectAsState()

    var serviceDate by remember { mutableStateOf(service?.date ?: emptyString()) }
    val serviceDateHelperText by viewModel.serviceDateHelperText.collectAsState()

    val selectedPaymentType by remember { mutableStateOf(service?.type ?: emptyString()) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)
    var typesExpanded by remember { mutableStateOf(false) }
    val typesValidation by remember { mutableStateOf(false) }
    val serviceTypesHelperText by viewModel.serviceTypesHelperText.collectAsState()

    val selectedRemember by remember { mutableStateOf(service?.remember ?: emptyString()) }
    val daysRemember = listOf(1, 2, 3)
    var daysExpanded by remember { mutableStateOf(false) }
    val rememberValidation by remember { mutableStateOf(false) }
    val serviceRememberHelperText by viewModel.serviceRememberHelperText.collectAsState()

    val selectedColor by remember { mutableStateOf(Color.White) }
    var colorPickerDialogOpen by remember { mutableStateOf(false) }

    // TODO: ARREGLAR VISIBILIDAD DE LOS COJONES
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isVisible }
            .filter { isVisible -> !isVisible }
            .collect { onDismiss() }
    }

    if (service != null) {
        initialValidations(
            viewModel,
            serviceName,
            servicePrice,
            selectedCategory,
            serviceDate,
            selectedPaymentType,
            selectedRemember
        )
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

                DefaultTextField(
                    DefaultTextFieldParams(
                        text = serviceName,
                        onTextChange = {
                            serviceName = it
                            viewModel.serviceName = it.text
                        },
                        wasTextFieldFocused = wasServiceNameFieldFocused.value,
                        onTextFieldFocusChange = { wasServiceNameFieldFocused.value = it },
                        textHelper = viewModel.serviceNameHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_name),
                        placeHolder = stringResource(id = R.string.service_name)
                    )
                ) { viewModel.validateServiceName() }

                ServiceSeparator(serviceNameHelperText.orElse { emptyString() })

                /*Text(stringResource(id = R.string.category, selectedCategory),
                    modifier = Modifier.clickable { categoriesExpanded = !categoriesExpanded }
                )*/

                /*DropdownMenu(
                    expanded = categoriesExpanded,
                    onDismissRequest = {
                        categoriesExpanded = false
                        categoriesValidation = false
                        validateServiceCategory(viewModel)
                    }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(onClick = {
                            selectedCategory = category.category
                            viewModel.serviceCategory = selectedCategory
                            categoriesExpanded = false
                            validateServiceCategory(viewModel)
                        }) {
                            Text(text = category.category)
                        }
                    }
                }*/

                // TODO: NO VALIDA
                CategoriesDropDownMenu(categories) {
                    viewModel.serviceCategory = it
                    validateServiceCategory(viewModel)
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
                            viewModel.serviceDate = serviceDate
                            viewModel.validateServiceDate()
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

                /*Text(stringResource(id = R.string.payment_type, selectedPaymentType),
                    modifier = Modifier
                        .clickable {
                            typesExpanded = !typesExpanded
                        }
                )*/

                TypesDropDownMenu(types) {
                    viewModel.serviceType = it
                    validateServiceType(viewModel)
                }

                /*DropdownMenu(
                    expanded = typesExpanded,
                    onDismissRequest = {
                        typesExpanded = false
                        typesValidation = false
                        validateServiceType(viewModel)
                    }
                ) {
                    types.forEach { type ->
                        DropdownMenuItem(onClick = {
                            selectedPaymentType = type.type
                            viewModel.serviceType = selectedPaymentType
                            typesExpanded = false
                            validateServiceType(viewModel)
                        }) {
                            Text(text = type.type)
                        }
                    }
                }*/

                HelperSeparator(serviceTypesHelperText.orElse { emptyString() })
                ServiceTypesHelperText(serviceTypesHelperText, typesValidation)
                ServiceSeparator(serviceTypesHelperText.orElse { emptyString() })

                DefaultTextField(
                    DefaultTextFieldParams(
                        text = servicePrice,
                        onTextChange = {
                            servicePrice = it
                            viewModel.servicePrice = it.text
                        },
                        wasTextFieldFocused = wasServicePriceFieldFocused.value,
                        onTextFieldFocusChange = { wasServicePriceFieldFocused.value = it},
                        textHelper = viewModel.servicePriceHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_price),
                        placeHolder = stringResource(id = R.string.service_price)
                    )
                ) { viewModel.validateServicePrice() }

                ServiceSeparator(servicePriceHelperText.orElse { emptyString() })

                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        /*Text(stringResource(id = R.string.remember_days_before, selectedRemember),
                            modifier = Modifier
                                .clickable {
                                    daysExpanded = !daysExpanded
                                }
                        )*/

                        RememberDropDownMenu(daysRemember) {
                            viewModel.serviceDate = it
                            validateServiceRemember(viewModel)
                        }

                        HelperSeparator(serviceRememberHelperText.orElse { emptyString() })
                        ServiceRememberHelperText(serviceRememberHelperText, rememberValidation)
                    }

                    Box(
                        modifier = Modifier
                            .size(dimen56)
                            .clip(CircleShape)
                            .background(selectedColor)
                            .border(dimen2, Color.Black, CircleShape)
                            .clickable {
                                colorPickerDialogOpen = true
                            }
                    )
                }
                Spacer(modifier = Modifier.height(dimen16))

                /*DropdownMenu(
                    expanded = daysExpanded,
                    onDismissRequest = {
                        daysExpanded = false
                        rememberValidation = false
                        validateServiceRemember(viewModel)
                    }
                ) {
                    daysRemember.forEach { day ->
                        DropdownMenuItem(onClick = {
                            selectedRemember = day.toString()
                            viewModel.serviceRemember = selectedRemember
                            daysExpanded = false
                            validateServiceRemember(viewModel)
                        }) {
                            Text(text = day.toString())
                        }
                    }
                }*/

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

private fun initialValidations(
    viewModel: HomeViewModel,
    serviceName: TextFieldValue,
    servicePrice: TextFieldValue,
    selectedCategory: String,
    serviceDate: String,
    selectedPaymentType: String,
    selectedRemember: String
) {
    viewModel.serviceName = serviceName.text
    viewModel.validateServiceName()

    viewModel.servicePrice = servicePrice.text
    viewModel.validateServicePrice()

    viewModel.serviceCategory = selectedCategory
    viewModel.validateServiceCategory()

    viewModel.serviceDate = serviceDate
    viewModel.validateServiceDate()

    viewModel.serviceType = selectedPaymentType
    viewModel.validateServiceType()

    viewModel.serviceRemember = selectedRemember
    viewModel.validateServiceRemember()
}

private fun validateServiceCategory(viewModel: HomeViewModel) = viewModel.validateServiceCategory()
private fun validateServiceType(viewModel: HomeViewModel) = viewModel.validateServiceType()
private fun validateServiceRemember(viewModel: HomeViewModel) = viewModel.validateServiceRemember()

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
private fun ServiceTypesHelperText(
    serviceTypesHelperText: String?,
    typesValidation: Boolean
) {
    if (!serviceTypesHelperText.isNullOrEmpty()) {
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
    if (!serviceCategoriesHelperText.isNullOrEmpty()) {
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

@Composable
private fun SaveButton(
    viewModel: HomeViewModel,
    buttonFunctionality: ButtonFunctionality
) {
    Button(
        onClick = {
            with(buttonFunctionality) {
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
                    val isServiceNameValid = validateServiceName()
                    val isServiceCategoryValid = validateServiceCategory()
                    val isServiceDateValid = validateServiceDate()
                    val isServiceTypeValid = validateServiceType()
                    val isServicePriceValid = validateServicePrice()

                    if (isServiceNameValid && isServiceCategoryValid && isServiceDateValid && isServiceTypeValid && isServicePriceValid) {
                        val serviceData = Service(
                            id = serviceId.orElse { emptyString() },
                            category = selectedCategory,
                            name = serviceName,
                            color = emptyString(),
                            date = serviceDate,
                            price = servicePrice,
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
                        validateServiceName()
                        validateServiceCategory()
                        validateServiceDate()
                        validateServiceType()
                        validateServicePrice()
                        validateServiceRemember()

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