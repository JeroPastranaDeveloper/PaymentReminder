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
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.pr.paymentreminder.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceBottomSheet
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                Surface (elevation = dimen4) {
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.app_name)) }
                    )
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
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
                                        viewModel.deleteService(service.id)
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
                }
            }
        )

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