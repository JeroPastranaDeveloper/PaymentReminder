package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceBottomSheet
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    fun handleAction(action: UiAction) {
        when (action) {
            UiAction.RemoveService -> removeService(viewModel)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            handleAction(action)
        }
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
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(dimen64))
                state.services.map { service ->
                    val dismissState = rememberDismissState()
                    ServiceCard(
                        service = service,
                        onClick = {
                            selectedService = service
                            showDialog = true
                        },
                        dismissState = dismissState,
                        deleteService = {
                            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                                viewModel.sendIntent(UiIntent.RemoveService(service.id))
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(dimen56))
                Spacer(modifier = Modifier.weight(1f))
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = spacing72, end = spacing16),
                onClick = {
                    selectedService = null
                    showBottomSheet = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = emptyString()
                )
            }

            if (showBottomSheet) {
                ServiceBottomSheet(selectedService, viewModel) {
                    selectedService = null
                    showBottomSheet = false
                }
            }

            if (showDialog) {
                ServiceDialog(
                    service = selectedService,
                    onClick = {
                        selectedService = null
                        showDialog = false
                    },
                    onEdit = {
                        showBottomSheet = false
                        showDialog = false

                        // TODO: Pasar únicamente la ID del servicio y que se recoja en AddServiceActivity.
                        // En AddServiceActivity, que filtre el servicio por el ID que se le ha pasado y de ahí que muestre los datos del servicio.

                        val intent = Intent(context, AddServiceActivity::class.java)

                        // Pasar los datos del servicio seleccionado al Intent
                        intent.putExtra("serviceId", selectedService?.id)
                        intent.putExtra("serviceCategory", selectedService?.category)
                        intent.putExtra("serviceColor", selectedService?.color)
                        intent.putExtra("serviceDate", selectedService?.date)
                        intent.putExtra("serviceName", selectedService?.name)
                        intent.putExtra("servicePrice", selectedService?.price)
                        intent.putExtra("serviceRemember", selectedService?.remember)
                        intent.putExtra("serviceType", selectedService?.type)
                        intent.putExtra("serviceImage", selectedService?.image)
                        intent.putExtra("serviceUrl", selectedService?.url)

                        // Iniciar la actividad
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

private fun removeService(
    viewModel: HomeViewModel
) {
    viewModel.sendIntent(UiIntent.GetServices)
}