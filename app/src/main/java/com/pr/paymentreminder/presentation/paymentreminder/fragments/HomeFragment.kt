package com.pr.paymentreminder.presentation.paymentreminder.fragments

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceBottomSheet
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    var selectedService by remember { mutableStateOf<Service?>(null) }
    val isLoading by viewModel.isLoading.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(dimen64))
                val scope = rememberCoroutineScope()
                viewModel.services.value.map { service ->
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
                                scope.launch {
                                    viewModel.deleteService(service.id)
                                    dismissState.reset()
                                    viewModel.getServices()
                                }
                            }
                        },
                        viewModel = viewModel
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
                        showBottomSheet = true
                        showDialog = false
                    }
                )
            }
        }
    }
}