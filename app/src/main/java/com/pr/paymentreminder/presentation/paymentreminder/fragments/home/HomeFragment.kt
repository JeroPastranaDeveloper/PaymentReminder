package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.CustomSnackBarConfig
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomSnackBar
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState
import com.pr.paymentreminder.ui.theme.Visible
import com.pr.paymentreminder.ui.theme.dimen0
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.dimen72
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing144
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72

@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.AddEditService -> addOrEditService(
                action.serviceId.orEmpty(),
                action.action,
                context
            )
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
            val snackBarHeight = if (state.showServiceDeletedSnackBar) dimen72 else dimen0
            val fabHeight = if (state.showServiceDeletedSnackBar) spacing144 else spacing72
            val animatedSnackBarHeight by animateDpAsState(
                targetValue = snackBarHeight,
                label = emptyString()
            )
            val animatedFABHeight by animateDpAsState(
                targetValue = fabHeight,
                label = emptyString()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(dimen64))

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = dimen56)
                            .verticalScroll(scrollState)
                    ) {
                        state.services.forEach { service ->
                            ServiceCard(
                                service = service,
                                onClick = {
                                    selectedService = service
                                    showDialog = true
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(dimen56))

                    Visible(state.showServiceDeletedSnackBar || state.showNewServiceSnackBar) {
                        CustomSnackBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = animatedSnackBarHeight),
                            config = CustomSnackBarConfig(
                                if (state.showNewServiceSnackBar) R.drawable.add else R.drawable.baseline_delete_24,
                                if (state.showNewServiceSnackBar) CustomSnackBarType.CREATE else CustomSnackBarType.DELETE
                            )
                        ) {
                            viewModel.sendIntent(UiIntent.RestoreDeletedService(state.serviceToRemove))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(animatedSnackBarHeight))
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = animatedFABHeight,
                        end = spacing16
                    ),
                onClick = {
                    viewModel.sendIntent(UiIntent.AddEditService(null, ButtonActions.ADD.name))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }

            if (showDialog) {
                ServiceDialog(
                    service = selectedService,
                    onClick = {
                        selectedService = null
                        showDialog = false
                    },
                    onEdit = {
                        showDialog = false
                        viewModel.sendIntent(
                            UiIntent.AddEditService(
                                selectedService?.id.orEmpty(),
                                ButtonActions.EDIT.name
                            )
                        )
                    },
                    onRemove = {
                        showDialog = false
                        viewModel.sendIntent(UiIntent.RemoveService(selectedService ?: Service()))
                    }
                )
            }
        }
    }
}

private fun addOrEditService(serviceId: String, action: String, context: Context) {
    val intent = Intent(context, AddServiceActivity::class.java)
    intent.putExtra("serviceId", serviceId)
    intent.putExtra("action", action)
    context.startActivity(intent)
}