package com.pr.paymentreminder.presentation.paymentreminder.compose

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun CardPlaceHolder() {
    val image = rememberAsyncImagePainter(model = emptyString())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(dimen100)
                    .padding(start = spacing4)
                    .align(Alignment.CenterVertically)
                    .placeholder(true),
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .padding(spacing4)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Text(
                    text = "ahdfkafdhai",
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = "ahdfkafdhaikaf",
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = "ahdfk",
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}