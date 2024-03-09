package com.pr.paymentreminder.presentation.paymentreminder.add_service

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.nameItem
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

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

        val serviceName by remember { mutableStateOf(TextFieldValue(state.service.name)) }

        var servicePrice by remember { mutableStateOf(TextFieldValue(state.service.price)) }

        var selectedCategory by remember { mutableStateOf(state.service.category) }
        val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)

        var serviceDate by remember { mutableStateOf(state.service.date) }

        var selectedType by remember { mutableStateOf(state.service.type) }
        val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)

        var selectedRemember by remember { mutableStateOf(state.service.remember) }
        val daysRemember = listOf(1, 2, 3)

        Content(state)
    }

    private fun handleAction(action: UiAction) {
        /*when(action) {

        }*/
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(state: UiState) {

        Surface(elevation = dimen4) {
            TopAppBar(
                title = { Text("_Editar servicio") }
            )
        }
        Spacer(modifier = Modifier.height(dimen64))

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .padding(spacing16)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    DefaultTextField(
                        params = DefaultTextFieldParams(
                            text = state.service.name,
                            onTextChange = {
                                name = it
                                viewModel.sendIntent(UiIntent.ValidateService(nameItem, it.text))
                            },
                            hasHelperText = state.serviceNameHelperText,
                            textHelperText = stringResource(id = R.string.invalid_service_name),
                            placeHolder = stringResource(id = R.string.service_name)
                        )
                    )
                }
            }
        }
    }
}