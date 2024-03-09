package com.pr.paymentreminder.presentation.paymentreminder.add_service

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.add_service.AddServiceViewModel
import com.pr.paymentreminder.ui.theme.spacing16

class AddServiceActivity : BaseActivity() {

    private val viewModel: AddServiceViewModel by viewModels()

    /*var serviceName by remember { mutableStateOf(TextFieldValue(service?.name.orEmpty())) }

    var servicePrice by remember { mutableStateOf(TextFieldValue(service?.price.orEmpty())) }

    var selectedCategory by remember { mutableStateOf(service?.category.orEmpty()) }
    val categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS)

    var serviceDate by remember { mutableStateOf(service?.date.orEmpty()) }

    var selectedType by remember { mutableStateOf(service?.type.orEmpty()) }
    val types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY)

    var selectedRemember by remember { mutableStateOf(service?.remember.orEmpty()) }
    val daysRemember = listOf(1, 2, 3)*/

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction)}
        val state by viewModel.state.collectAsState(UiState())

        val serviceId = intent.getStringExtra("serviceId")
        val serviceCategory = intent.getStringExtra("serviceCategory")
        val serviceColor = intent.getStringExtra("serviceColor")
        val serviceDate = intent.getStringExtra("serviceDate")
        val serviceName = intent.getStringExtra("serviceName")
        val servicePrice = intent.getStringExtra("servicePrice")
        val serviceRemember = intent.getStringExtra("serviceRemember")
        val serviceType = intent.getStringExtra("serviceType")
        val serviceImage = intent.getStringExtra("serviceImage")
        val serviceUrl = intent.getStringExtra("serviceUrl")

        // Crear una instancia de Service con los datos recogidos
        val service = Service(
            serviceId.orEmpty(),
            serviceCategory.orEmpty(),
            serviceColor.orEmpty(),
            serviceDate.orEmpty(),
            serviceName.orEmpty(),
            servicePrice.orEmpty(),
            serviceRemember.orEmpty(),
            serviceType.orEmpty(),
            serviceImage.orEmpty(),
            serviceUrl.orEmpty()
        )

        Content(state)
    }

    private fun handleAction(action: UiAction) {
        /*when(action) {

        }*/
    }

    @Composable
    private fun Content(state: UiState) {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DefaultTextField(params = DefaultTextFieldParams(
                serviceName
            ))
        }
    }

}