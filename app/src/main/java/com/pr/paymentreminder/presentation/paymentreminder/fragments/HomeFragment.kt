package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pr.paymentreminder.base.findInHierarchy
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.add_service.AddServiceActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomToastInfo
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.compose.ToastOwnerInApp
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Log.d("ESTOY EN", "HOME")
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

                        is UiAction.ShowRemovedToast -> showRemovedToast(action.toastInfo)
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
                                ServiceCard(
                                    service = service,
                                    onClick = {
                                        selectedService = service
                                        showDialog = true
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))

                            Spacer(modifier = Modifier.height(dimen56))
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = spacing72, end = spacing16),
                            onClick = {
                                viewModel.sendIntent(
                                    UiIntent.AddEditService(
                                        null,
                                        ButtonActions.ADD.name
                                    )
                                )
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
                                    viewModel.sendIntent(
                                        UiIntent.RemoveService(
                                            selectedService?.id.orEmpty(),
                                            selectedService ?: Service()
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showRemovedToast(info: CustomToastInfo) {
        findInHierarchy<ToastOwnerInApp>()?.showToastInApp(info)
    }

    private fun addOrEditService(serviceId: String, action: String, context: Context) {
        val intent = Intent(context, AddServiceActivity::class.java)
        intent.putExtra("serviceId", serviceId)
        intent.putExtra("action", action)
        context.startActivity(intent)
    }
}