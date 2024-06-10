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
import kotlin.random.Random

@Composable
fun CardPlaceHolder() {
    val image = rememberAsyncImagePainter(model = emptyString())
    fun randomString(length: Int) = Random.nextBytes(length).joinToString("") { "%02x".format(it) }.substring(0, length)

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
                    .padding(start = spacing8, top = spacing4, bottom = spacing4)
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
                    text = randomString(10),
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = randomString(15),
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(dimen8))
                Text(
                    text = randomString(5),
                    modifier = Modifier.padding(spacing4).placeholder(visible = true),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}