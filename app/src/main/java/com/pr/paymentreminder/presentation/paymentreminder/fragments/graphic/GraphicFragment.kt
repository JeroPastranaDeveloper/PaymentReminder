package com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.paymentreminder.compose.DonutChart
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomChip
import com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewContract.UiState
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16

@Composable
fun GraphicFragment(viewModel: GraphicViewModel) {
    val state by viewModel.state.collectAsState(UiState())

    Column(
        modifier = Modifier
            .padding(start = spacing16, end = spacing16, bottom = spacing16)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(dimen64))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            state.serviceTypes.forEach { (stringId, serviceType) ->
                CustomChip(
                    title = stringResource(id = stringId),
                    onClick = {
                        viewModel.sendIntent(UiIntent.GetFilteredServices(serviceType))
                    },
                    selected = state.selectedChip == serviceType
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
                        .padding(spacing16)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                DonutChart(state.services)
            }

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(id = R.string.weekly_expenditure, state.weeklyExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.monthly_expenditure, state.monthlyExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.monthly_total_expenditure, state.monthlyTotalExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.yearly_expenditure, state.yearlyExpenditure.orEmpty()))
                Text(text = stringResource(id = R.string.yearly_total_expenditure, state.yearlyTotalExpenditure.orEmpty()))
            }

            Spacer(modifier = Modifier.height(dimen56))
        }
    }
}