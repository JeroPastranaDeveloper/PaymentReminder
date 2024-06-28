package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceActivity
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState

@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

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

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.sendIntent(UiIntent.CheckSnackBarConfig)
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    HomeFactory(
        state = state,
        action = HomeActions(
            onClickFAB = { serviceId, action ->
                viewModel.sendIntent(UiIntent.AddEditService(serviceId, action))
            },
            onDismissSnackBar = {
                viewModel.sendIntent(UiIntent.OnDismissSnackBar)
            },
            onFilterCategory = {
                viewModel.sendIntent(UiIntent.FilterCategory(it))
            },
            onRemoveService = {
                viewModel.sendIntent(UiIntent.RemoveService(it))
            },
            onRestoreDeletedService = {
                viewModel.sendIntent(UiIntent.RestoreDeletedService(it))
            }
        )
    )
}

private fun addOrEditService(serviceId: String, action: String, context: Context) {
    val intent = Intent(context, AddServiceActivity::class.java)
    intent.putExtra("serviceId", serviceId)
    intent.putExtra("action", action)
    context.startActivity(intent)
}

data class HomeActions(
    val onClickFAB: (serviceId: String?, action: String) -> Unit,
    val onDismissSnackBar: () -> Unit,
    val onFilterCategory: (categoryName: String) -> Unit,
    val onRemoveService: (selectedService: Service) -> Unit,
    val onRestoreDeletedService: (selectedService: Service) -> Unit,
)
