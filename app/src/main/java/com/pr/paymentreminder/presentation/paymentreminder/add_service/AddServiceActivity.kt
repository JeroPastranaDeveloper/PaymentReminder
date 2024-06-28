package com.pr.paymentreminder.presentation.paymentreminder.add_service

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.ServiceItem
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddServiceActivity : BaseActivity() {

    private val viewModel: AddServiceViewModel by viewModels()
    private lateinit var serviceId: String
    private lateinit var action: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceId = intent.getStringExtra("serviceId").toString()
        action = intent.getStringExtra("action").toString()
        viewModel.sendIntent(UiIntent.CheckIntent(serviceId, action))
    }

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())
        Content(state)
    }

    private fun handleAction(action: UiAction) {
        when (action) {
            UiAction.GoBack -> finish()
        }
    }

    @Composable
    private fun Content(state: UiState) {
        AddServiceFactory(
            state = state,
            action = AddServiceActions(
                onBack = {
                    finish()
                },
                onValidateAndSave = {
                    viewModel.sendIntent(UiIntent.ValidateAndSave(it))
                },
                onValidateService = { serviceItem, value ->
                    viewModel.sendIntent(UiIntent.ValidateService(serviceItem, value))
                }
            ),
            serviceId = serviceId
        )
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
                OutlinedTextField(
                    value = emptyString(),
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8),
                    singleLine = false
                )

                OutlinedTextField(
                    value = emptyString(),
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_price)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8),
                    singleLine = false
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
                OutlinedTextField(
                    value = "Comentarios jeje",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_comments)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8),
                    singleLine = true
                )

                OutlinedTextField(
                    value = "URL de la imagen",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_image_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8),
                    singleLine = true
                )

                OutlinedTextField(
                    value = "URL del servicio",
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.service_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing8),
                    singleLine = true
                )

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.btn_save))
                }
            }
        }
    }
}

data class AddServiceActions(
    val onBack: () -> Unit,
    val onValidateAndSave: (service: Service) -> Unit,
    val onValidateService: (item: ServiceItem, value: String) -> Unit
)