package com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.SmallServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewContract.UiState
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.pastelBlue
import com.pr.paymentreminder.ui.theme.pastelGreen
import com.pr.paymentreminder.ui.theme.pastelPurple
import com.pr.paymentreminder.ui.theme.pastelRed
import com.pr.paymentreminder.ui.theme.semiBlack
import com.pr.paymentreminder.ui.theme.spacing56
import com.pr.paymentreminder.ui.theme.spacing64
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun PaymentsHistoryFragment(viewModel: PaymentsHistoryViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current

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
                    ServicesFlowRow(state.services)
                }

                Spacer(modifier = Modifier.size(dimen56))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ServicesFlowRow(
    services: List<Service>
) {
    val colors: List<Color> = listOf(pastelRed, pastelBlue, pastelGreen, pastelPurple, semiBlack)
    FlowRow(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(spacing8)
    ) {
        services.forEach { service ->
            val randomColor = colors.random()
            SmallServiceCard(service, randomColor)
        }
    }
}