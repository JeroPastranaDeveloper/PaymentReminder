package com.pr.paymentreminder.presentation.paymentreminder.fragments

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.presentation.paymentreminder.compose.DonutChart
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServicesChip
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.graphic.GraphicViewModel
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16

@Composable
fun GraphicFragment(viewModel: GraphicViewModel) {

    val state by viewModel.state.collectAsState(UiState())
    var selectedChip by remember { mutableStateOf(Constants.ALL_SERVICES) }

    val paymentWeekly = PaymentType.WEEKLY.type
    val paymentMonthly = PaymentType.MONTHLY.type
    val paymentYearly = PaymentType.YEARLY.type

    fun handleAction(action: UiAction) {
        when (action) {
            else -> {}
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            handleAction(action)
        }
    }

    val serviceTypes = listOf(
        Pair(R.string.all_services, Constants.ALL_SERVICES),
        Pair(R.string.weekly_services, paymentWeekly),
        Pair(R.string.monthly_services, paymentMonthly),
        Pair(R.string.yearly_services, paymentYearly)
    )

    Column(
        modifier = Modifier
            .padding(start = spacing16, end = spacing16, bottom = spacing16)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(dimen64))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            serviceTypes.forEach { (stringId, serviceType) ->
                ServicesChip(
                    title = stringResource(id = stringId),
                    onClick = { viewModel.sendIntent(UiIntent.GetFilteredServices(serviceType)) },
                    selected = selectedChip == serviceType,
                    onSelectedChange = { selectedChip = it }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (state.services.isEmpty()) {
                Text(
                    stringResource(id = R.string.no_services), modifier = Modifier
                        .padding(top = spacing16)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                DonutChart(state.services)
            }

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(id = R.string.weekly_expenditure, state.weeklyExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.monthly_expenditure, state.monthlyExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.yearly_expenditure, state.yearlyExpenditure.orEmpty()))
            }
        }
    }
}