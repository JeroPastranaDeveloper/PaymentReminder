package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCard(service: Service, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = if (service.image != null) {
                    rememberAsyncImagePainter(model = Uri.parse(service.image))
                } else {
                    painterResource(id = R.drawable.ic_launcher_background)
                },
                contentDescription = emptyString(),
                modifier = Modifier
                    .size(dimen100)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .padding(start = spacing4)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Text(
                    text = service.name,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = service.date,
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = "${service.price}${Constants.EURO}",
                    modifier = Modifier.padding(spacing4),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}