package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.SaveButtonFunctionality
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing8
import kotlinx.coroutines.flow.filter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServiceBottomSheet(service: Service?, viewModel: HomeViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState(UiState())

    var imageUrl by remember { mutableStateOf(TextFieldValue(service?.image ?: emptyString())) }
    var serviceUrl by remember { mutableStateOf(TextFieldValue(service?.url ?: emptyString())) }

    /*val imageUriString = service?.image
    val imageUri = remember { mutableStateOf(imageUriString?.let { Uri.parse(it) }) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }*/

    var serviceName by remember { mutableStateOf(TextFieldValue(service?.name ?: emptyString())) }

    var servicePrice by remember { mutableStateOf(TextFieldValue(service?.price ?: emptyString())) }

    var selectedCategory by remember { mutableStateOf(service?.category ?: emptyString()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)

    var serviceDate by remember { mutableStateOf(service?.date ?: emptyString()) }

    var selectedPaymentType by remember { mutableStateOf(service?.type ?: emptyString()) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)

    var selectedRemember by remember { mutableStateOf(service?.remember ?: emptyString()) }
    val daysRemember = listOf(1, 2, 3)

    /*val selectedColor by remember { mutableStateOf(Color.White) }
    var colorPickerDialogOpen by remember { mutableStateOf(false) }*/

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

                TextField(
                    value = serviceUrl,
                    onValueChange = { serviceUrl = it },
                    label = { Text(stringResource(id = R.string.service_url)) },
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
                            viewModel.sendIntent(UiIntent.ValidateServiceName(it.text))
                        },
                        hasHelperText = state.serviceNameHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_name),
                        placeHolder = stringResource(id = R.string.service_name)
                    )
                )

                ServiceSeparator()

                CategoriesDropDownMenu(
                    categories = categories,
                    initialSelectedCategory = selectedCategory,
                    hasHelperText = state.serviceCategoryHelperText,
                    textHelperText = stringResource(id = R.string.invalid_service_category)
                ) {
                    selectedCategory = it
                    viewModel.sendIntent(UiIntent.ValidateServiceCategory(selectedCategory))
                }

                ServiceSeparator()

                val datePickerDialog = remember {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, dayOfMonth)
                            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
                            serviceDate = dateFormat.format(calendar.time)
                        },
                        Calendar.getInstance()[Calendar.YEAR],
                        Calendar.getInstance()[Calendar.MONTH],
                        Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                    ).apply {
                        setOnDismissListener {
                            viewModel.sendIntent(UiIntent.ValidateServiceDate(serviceDate))
                        }
                    }
                }

                Text(
                    text = stringResource(id = R.string.payment_date, serviceDate),
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )

                if (state.serviceDateHelperText) {
                    ServiceSeparator()
                    HelperText(stringResource(id = R.string.invalid_service_date))
                }

                ServiceSeparator()

                TypesDropDownMenu(
                    types = types,
                    initialSelectedType = selectedPaymentType,
                    hasHelperText = state.serviceTypeHelperText,
                    textHelperText = stringResource(id = R.string.invalid_service_type)
                ) {
                    selectedPaymentType = it
                    viewModel.sendIntent(UiIntent.ValidateServiceType(selectedPaymentType))
                }

                ServiceSeparator()

                DefaultTextField(
                    DefaultTextFieldParams(
                        text = servicePrice,
                        onTextChange = {
                            servicePrice = it
                            viewModel.sendIntent(UiIntent.ValidateServicePrice(it.text))
                        },
                        hasHelperText = state.servicePriceHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_price),
                        placeHolder = stringResource(id = R.string.service_price)
                    )
                )

                ServiceSeparator()

                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {

                        RememberDropDownMenu(
                            rememberDays = daysRemember,
                            initialSelectedDay = selectedRemember,
                            hasHelperText = state.serviceRememberHelperText,
                            textHelperText = stringResource(id = R.string.invalid_service_remember)
                        ) {
                            selectedRemember = it
                            viewModel.sendIntent(UiIntent.ValidateServiceRemember(it))
                        }
                    }

                    /*Box(
                        modifier = Modifier
                            .size(dimen56)
                            .clip(CircleShape)
                            .background(selectedColor)
                            .border(dimen2, Color.Black, CircleShape)
                            .clickable {
                                colorPickerDialogOpen = true
                            }
                    )*/
                }

                ServiceSeparator()

                val serviceId = service?.id
                SaveButton(
                    viewModel,
                    saveButtonFunctionality = SaveButtonFunctionality(
                        serviceName = serviceName,
                        selectedCategory = selectedCategory,
                        serviceDate = serviceDate,
                        selectedPaymentType = selectedPaymentType,
                        servicePrice = servicePrice,
                        serviceId = serviceId,
                        selectedRemember = selectedRemember,
                        imageUri = imageUrl,
                        serviceUrl = serviceUrl,
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
    viewModel.sendIntent(UiIntent.ValidateServiceName(serviceName.text))
    viewModel.sendIntent(UiIntent.ValidateServicePrice(servicePrice.text))
    viewModel.sendIntent(UiIntent.ValidateServiceCategory(selectedCategory))
    viewModel.sendIntent(UiIntent.ValidateServiceDate(serviceDate))
    viewModel.sendIntent(UiIntent.ValidateServiceType(selectedPaymentType))
    viewModel.sendIntent(UiIntent.ValidateServiceRemember(selectedRemember))
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
                            image = imageUri.text,
                            url = serviceUrl.text
                        )

                        if (service != null) {
                            updateService(serviceData, viewModel)
                        } else {
                            createService(serviceData, viewModel)
                        }
                        viewModel.sendIntent(UiIntent.GetServices)
                        onDismiss()

                    } else {
                        /*initialValidations(
                            viewModel,
                            serviceName,
                            servicePrice,
                            selectedCategory,
                            serviceDate,
                            selectedPaymentType,
                            selectedRemember
                        )*/

                        Toast.makeText(context, R.string.invalid_data, Toast.LENGTH_LONG).show()
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
fun ServiceSeparator() {
    Spacer(modifier = Modifier.height(dimen16))
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
        image = service.image,
        url = service.url
    )
    viewModel.sendIntent(UiIntent.UpdateService(
        service.id.orElse { emptyString() },
        updatedServiceData
    ))
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
        image = serviceData.image,
        url = serviceData.url
    )
    viewModel.sendIntent(UiIntent.CreateService(newService))
}