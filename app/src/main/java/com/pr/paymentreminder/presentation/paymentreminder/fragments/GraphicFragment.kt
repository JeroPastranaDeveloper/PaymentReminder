package com.pr.paymentreminder.presentation.paymentreminder.fragments

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.presentation.paymentreminder.compose.DonutChart
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.GraphicViewModel
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing4

@Composable
fun GraphicFragment(graphicViewModel: GraphicViewModel) {
    var services by remember(graphicViewModel.services) {
        mutableStateOf(graphicViewModel.services.value)
    }

    var selectedChip by remember { mutableStateOf(Constants.ALL_SERVICES) }

    if (selectedChip == Constants.ALL_SERVICES) {
        services = graphicViewModel.services.value
    }

    Column(
        modifier = Modifier
            .padding(spacing16)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            ServicesChip(
                title = stringResource(id = R.string.all_services),
                onClick = { services = graphicViewModel.services.value },
                selected = selectedChip == Constants.ALL_SERVICES,
                onSelectedChange = { selectedChip = it }
            )
            ServicesChip(
                title = stringResource(id = R.string.weekly_services),
                onClick = { services = graphicViewModel.weeklyServices.value },
                selected = selectedChip == PaymentType.WEEKLY.type,
                onSelectedChange = { selectedChip = it }
            )
            ServicesChip(
                title = stringResource(id = R.string.monthly_services),
                onClick = { services = graphicViewModel.monthlyServices.value },
                selected = selectedChip == PaymentType.MONTHLY.type,
                onSelectedChange = { selectedChip = it }
            )
            ServicesChip(
                title = stringResource(id = R.string.yearly_services),
                onClick = { services = graphicViewModel.annualServices.value },
                selected = selectedChip == PaymentType.YEARLY.type,
                onSelectedChange = { selectedChip = it }
            )
        }

        if (services.isEmpty()) {
            Text(stringResource(id = R.string.no_services), modifier = Modifier.padding(top = spacing16).align(Alignment.CenterHorizontally))
        } else {
            DonutChart(services)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ServicesChip(title: String, onClick: () -> Unit, selected: Boolean, onSelectedChange: (String) -> Unit) {
    Chip(
        onClick = {
            onClick()
            onSelectedChange(title)
        },
        modifier = Modifier.padding(end = spacing4),
        colors = ChipDefaults.chipColors(
            contentColor = if (selected) Color.White else Color.Black,
            backgroundColor = if (selected) Color.Gray else Color.LightGray
        )
    ) {
        Text(title)
    }
}