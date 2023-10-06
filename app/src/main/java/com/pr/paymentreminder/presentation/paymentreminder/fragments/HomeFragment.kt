package com.pr.paymentreminder.presentation.paymentreminder.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun HomeFragment(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        for (service in viewModel.services.value) {
            ServiceCard(service)
        }
        Spacer(modifier = Modifier.height(dimen56))
    }
}

@Composable
private fun ServiceCard(service: Service) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFEADDFF))
                .fillMaxWidth()
                .padding(dimen16),
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = emptyString(),
                modifier = Modifier
                    .width(dimen100)
                    .height(dimen100)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit,
            )

            Column(modifier = Modifier
                .padding(start = spacing4)
                .align(Alignment.CenterVertically)) {
                Text(
                    text = service.name,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(spacing8))
                Text(
                    text = service.date,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(spacing8))
                Text(
                    text = "${service.price}€",
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}