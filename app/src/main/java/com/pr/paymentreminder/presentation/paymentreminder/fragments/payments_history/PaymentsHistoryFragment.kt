package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomDialog
import com.pr.paymentreminder.presentation.paymentreminder.compose.SmallServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiState
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.getPastelColors
import com.pr.paymentreminder.ui.theme.spacing56
import com.pr.paymentreminder.ui.theme.spacing64
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun PaymentsHistoryFragment(viewModel: PaymentsHistoryViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    fun handleAction(action: UiAction) {
        when (action) {
            is UiAction.EditService -> editService(
                action.serviceId,
                ButtonActions.EDIT_PAID.name,
                context
            )
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            handleAction(action)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.sendIntent(UiIntent.GetServices)
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = spacing64)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = spacing56)
                        .verticalScroll(scrollState)
                ) {
                    ServicesFlowRow(state.services, viewModel)
                }

                if (state.showRemoveServiceDialog) {
                    CustomDialog(
                        titleText = stringResource(id = R.string.remove_service_title),
                        bodyText = stringResource(id = R.string.remove_service_body),
                        onAccept = { viewModel.sendIntent(UiIntent.DeleteService) },
                        onCancel = { viewModel.sendIntent(UiIntent.ShowDeleteServiceDialog(hasToShow = false)) }
                    )
                }

                Spacer(modifier = Modifier.size(dimen56))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ServicesFlowRow(
    services: List<Service>,
    viewModel: PaymentsHistoryViewModel
) {
    FlowRow(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        services.forEachIndexed { index, service ->
            val color = getPastelColors()[index % getPastelColors().size]
            SmallServiceCard(service, color,
                onClick = {
                    viewModel.sendIntent(UiIntent.EditService(service.id))
                },
                onLongClick = {
                    viewModel.sendIntent(UiIntent.ShowDeleteServiceDialog(service.id, true))
                }
            )
        }
    }
}

private fun editService(serviceId: String, action: String, context: Context) {
    val intent = Intent(context, AddServiceActivity::class.java)
    intent.putExtra("serviceId", serviceId)
    intent.putExtra("action", action)
    context.startActivity(intent)
}