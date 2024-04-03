package com.pr.paymentreminder.presentation.paymentreminder.add_service

import android.app.DatePickerDialog
import android.os.Bundle
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.SaveButtonFunctionality
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
import com.pr.paymentreminder.presentation.paymentreminder.compose.SaveButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.TypesDropDownMenu
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.orFalse
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddServiceActivity : BaseActivity() {

    private val viewModel: AddServiceViewModel by viewModels()
    private lateinit var serviceId: String
    private lateinit var action: String

    override fun onCreate(savedInstanceState: Bundle?) {
        serviceId = intent.getStringExtra("serviceId").toString()
        action = intent.getStringExtra("action").toString()

        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())

        viewModel.sendIntent(UiIntent.CheckIntent(serviceId, action))

        Content(state)
    }

    private fun handleAction(action: UiAction) {
        when(action) {
            UiAction.GoBack -> finish()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(state: UiState) {
        val context = LocalContext.current

        var serviceName by remember { mutableStateOf(state.service.name) }
        var servicePrice by remember { mutableStateOf(state.service.price) }

        var selectedCategory by remember { mutableStateOf(state.service.category) }
        val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)

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
                        IconButton(onClick = { finish() }) {
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
                            .padding(spacing16),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DefaultTextField(
                            params = DefaultTextFieldParams(
                                text = TextFieldValue(serviceName),
                                onTextChange = {
                                    serviceName = it.text
                                    viewModel.sendIntent(
                                        UiIntent.ValidateService(
                                            nameItem,
                                            it.text
                                        )
                                    )
                                },
                                hasHelperText = state.serviceHelperText?.nameHelperText.orFalse(),
                                textHelperText = stringResource(id = R.string.invalid_service_name),
                                placeHolder = stringResource(id = R.string.service_name)
                            )
                        )

                        DefaultTextField(
                            DefaultTextFieldParams(
                                text = TextFieldValue(servicePrice),
                                onTextChange = {
                                    servicePrice = it.text
                                    viewModel.sendIntent(
                                        UiIntent.ValidateService(
                                            priceItem,
                                            it.text
                                        )
                                    )
                                },
                                hasHelperText = state.serviceHelperText?.priceHelperText.orFalse(),
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
                                    val dateFormat =
                                        SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
                                    serviceDate = dateFormat.format(calendar.time)
                                },
                                Calendar.getInstance()[Calendar.YEAR],
                                Calendar.getInstance()[Calendar.MONTH],
                                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                            ).apply {
                                setOnDismissListener {
                                    viewModel.sendIntent(
                                        UiIntent.ValidateService(
                                            dateItem,
                                            serviceDate
                                        )
                                    )
                                }
                            }
                        }

                        Text(
                            text = stringResource(id = R.string.payment_date, serviceDate),
                            modifier = Modifier.clickable { datePickerDialog.show() }
                        )
                        Spacer(modifier = Modifier.height(dimen16))

                        if (state.serviceHelperText?.dateHelperText.orFalse()) HelperText(stringResource(id = R.string.invalid_service_date))

                        TypesDropDownMenu(
                            types = types,
                            initialSelectedType = state.service.type,
                            hasHelperText = state.serviceHelperText?.typeHelperText.orFalse(),
                            textHelperText = stringResource(id = R.string.invalid_service_type)
                        ) {
                            selectedType = it
                            viewModel.sendIntent(UiIntent.ValidateService(typeItem, selectedType))
                        }

                        RememberDropDownMenu(
                            initialSelectedDay = selectedRemember,
                            hasHelperText = state.serviceHelperText?.rememberHelperText.orFalse(),
                            textHelperText = stringResource(id = R.string.invalid_service_remember)
                        ) {
                            selectedRemember = it
                            viewModel.sendIntent(UiIntent.ValidateService(rememberItem, it))
                        }

                        CategoriesDropDownMenu(
                            categories = categories,
                            initialSelectedCategory = selectedCategory,
                            hasHelperText = state.serviceHelperText?.categoryHelperText.orFalse(),
                            textHelperText = stringResource(id = R.string.invalid_service_category)
                        ) {
                            selectedCategory = it
                            viewModel.sendIntent(
                                UiIntent.ValidateService(
                                    categoryItem,
                                    selectedCategory
                                )
                            )
                        }

                        TextField(
                            value = comments,
                            onValueChange = { comments = it },
                            label = { Text(stringResource(id = R.string.service_comments)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing8)
                                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                            singleLine = false
                        )

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

                        Spacer(modifier = Modifier.weight(1f))

                        SaveButton(
                            viewModel,
                            saveButtonFunctionality = SaveButtonFunctionality(
                                serviceName = TextFieldValue(serviceName),
                                selectedCategory = selectedCategory,
                                serviceDate = serviceDate,
                                selectedPaymentType = selectedType,
                                servicePrice = TextFieldValue(servicePrice),
                                serviceId = state.service.id,
                                selectedRemember = selectedRemember,
                                imageUri = TextFieldValue(imageUrl),
                                comments = comments,
                                serviceUrl = TextFieldValue(serviceUrl),
                                service = state.service,
                                action = state.action,
                                context = context
                            )
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    private fun AddServiceActivityPreview() {
        Column {
            Surface {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.edit_service_title)) },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing16),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                DefaultTextField(
                    params = DefaultTextFieldParams(
                        text = TextFieldValue("Nombre"),
                        onTextChange = {},
                        hasHelperText = false,
                        textHelperText = stringResource(id = R.string.invalid_service_name),
                        placeHolder = stringResource(id = R.string.service_name)
                    )
                )

                DefaultTextField(
                    DefaultTextFieldParams(
                        text = TextFieldValue("Precio"),
                        onTextChange = {},
                        hasHelperText = false,
                        textHelperText = stringResource(id = R.string.invalid_service_price),
                        placeHolder = stringResource(id = R.string.service_price)
                    )
                )

                Text(
                    text = stringResource(id = R.string.payment_date, "03/05/2000")
                )
                Spacer(modifier = Modifier.height(dimen16))

                Text(
                    text = stringResource(id = R.string.payment_type, "Anual"),
                )
                Spacer(modifier = Modifier.height(dimen16))
                Text(
                    text = stringResource(id = R.string.remember_day_before, "3"),
                )
                Spacer(modifier = Modifier.height(dimen16))
                Text(
                    text = stringResource(id = R.string.category, "Amazon")
                )
                Spacer(modifier = Modifier.height(dimen16))
                TextField(
                    value = "Comentarios jeje",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_comments)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                    singleLine = true
                )

                TextField(
                    value = "URL de la imagen",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_image_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                    singleLine = true
                )

                TextField(
                    value = "URL del servicio",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8)
                        .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
                    singleLine = true
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.btn_save))
                }
            }
        }
    }
}