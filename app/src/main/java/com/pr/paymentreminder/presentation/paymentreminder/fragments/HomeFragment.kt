package com.pr.paymentreminder.presentation.paymentreminder.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceBottomSheet
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing72

@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            viewModel.services.value.map { service ->
                ServiceCard(service) {
                    selectedService = service
                    showBottomSheet = true
                }
            }
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
            ServiceBottomSheet(service = selectedService, viewModel) {
                selectedService = null
                showBottomSheet = false
            }
        }

        /*selectedService?.let {
            ServiceBottomSheet(service = it, viewModel) {
                selectedService = null
                showBottomSheet = false
            }
        }*/
    }
}