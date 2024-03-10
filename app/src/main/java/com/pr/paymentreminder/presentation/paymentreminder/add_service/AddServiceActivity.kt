package com.pr.paymentreminder.presentation.paymentreminder.add_service

import android.app.DatePickerDialog
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.categoryItem
import com.pr.paymentreminder.data.model.dateItem
import com.pr.paymentreminder.data.model.nameItem
import com.pr.paymentreminder.data.model.priceItem
import com.pr.paymentreminder.data.model.rememberItem
import com.pr.paymentreminder.data.model.typeItem
import com.pr.paymentreminder.presentation.paymentreminder.compose.CategoriesDropDownMenu
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.HelperText
import com.pr.paymentreminder.presentation.paymentreminder.compose.RememberDropDownMenu
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceSeparator
import com.pr.paymentreminder.presentation.paymentreminder.compose.TypesDropDownMenu
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.orEmpty
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddServiceActivity : BaseActivity() {

    private val viewModel: AddServiceViewModel by viewModels()

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())

        val serviceId = intent.getStringExtra("serviceId")
        serviceId?.let {
            viewModel.sendIntent(UiIntent.GetService(it))
        }

        Content(state)
    }

    private fun handleAction(action: UiAction) {
        /*when(action) {

        }*/
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(state: UiState) {
        val context = LocalContext.current

        var serviceName by remember { mutableStateOf(state.serviceTextField.name.orEmpty()) }

        var servicePrice by remember { mutableStateOf(state.serviceTextField.price.orEmpty()) }

        var selectedCategory by remember { mutableStateOf(state.service.category) }
        val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)

        var serviceDate by remember { mutableStateOf(state.service.date) }

        var selectedType by remember { mutableStateOf(state.service.type) }
        val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)

        var selectedRemember by remember { mutableStateOf(state.service.remember) }
        val daysRemember = listOf(1, 2, 3)

        var imageUrl by remember { mutableStateOf(state.serviceTextField.image.orEmpty()) }
        var serviceUrl by remember { mutableStateOf(state.serviceTextField.url.orEmpty()) }

        Surface(elevation = dimen4) {
            TopAppBar(
                title = { Text("_Editar servicio") }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .padding(spacing16)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(dimen64))
                    DefaultTextField(
                        params = DefaultTextFieldParams(
                            text = serviceName,
                            onTextChange = {
                                serviceName = it
                                viewModel.sendIntent(UiIntent.ValidateService(nameItem, it.text))
                            },
                            hasHelperText = state.serviceNameHelperText,
                            textHelperText = stringResource(id = R.string.invalid_service_name),
                            placeHolder = stringResource(id = R.string.service_name)
                        )
                    )

                    DefaultTextField(
                        DefaultTextFieldParams(
                            text = servicePrice,
                            onTextChange = {
                                servicePrice = it
                                viewModel.sendIntent(UiIntent.ValidateService(priceItem, it.text))
                            },
                            hasHelperText = state.servicePriceHelperText,
                            textHelperText = stringResource(id = R.string.invalid_service_price),
                            placeHolder = stringResource(id = R.string.service_price)
                        )
                    )

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
                                viewModel.sendIntent(UiIntent.ValidateService(dateItem, serviceDate))
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

                    TypesDropDownMenu(
                        types = types,
                        initialSelectedType = selectedType,
                        hasHelperText = state.serviceTypeHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_type)
                    ) {
                        selectedType = it
                        viewModel.sendIntent(UiIntent.ValidateService(typeItem, selectedType))
                    }

                    RememberDropDownMenu(
                        rememberDays = daysRemember,
                        initialSelectedDay = selectedRemember,
                        hasHelperText = state.serviceRememberHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_remember)
                    ) {
                        selectedRemember = it
                        viewModel.sendIntent(UiIntent.ValidateService(rememberItem, it))
                    }

                    CategoriesDropDownMenu(
                        categories = categories,
                        initialSelectedCategory = selectedCategory,
                        hasHelperText = state.serviceCategoryHelperText,
                        textHelperText = stringResource(id = R.string.invalid_service_category)
                    ) {
                        selectedCategory = it
                        viewModel.sendIntent(UiIntent.ValidateService(categoryItem, selectedCategory))
                    }

                    androidx.compose.material3.TextField(
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

                    androidx.compose.material3.TextField(
                        value = serviceUrl,
                        onValueChange = { serviceUrl = it },
                        label = { Text(stringResource(id = R.string.service_url)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing8)
                            .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                        singleLine = true
                    )

                    /*val serviceId = state.service.id
                    SaveButton(
                        viewModel,
                        saveButtonFunctionality = SaveButtonFunctionality(
                            serviceName = serviceName,
                            selectedCategory = selectedCategory,
                            serviceDate = serviceDate,
                            selectedPaymentType = selectedType,
                            servicePrice = servicePrice,
                            serviceId = serviceId,
                            selectedRemember = selectedRemember,
                            imageUri = imageUrl,
                            serviceUrl = serviceUrl,
                            service = service,
                            onDismiss = onDismiss,
                            context = context
                        )
                    )*/
                }
            }
        }
    }
}