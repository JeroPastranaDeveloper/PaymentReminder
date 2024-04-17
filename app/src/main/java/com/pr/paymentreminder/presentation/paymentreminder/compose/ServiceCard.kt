package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.annotation.SuppressLint
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ServiceCard(
    service: Service,
    onClick: () -> Unit
    // color: Color
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8),
        onClick = onClick
        /*colors = CardColors(
            containerColor = color,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
        )*/
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                }.build()

            val imageUrl =
                service.image.takeIf { it.orEmpty().isNotEmpty() }.orElse { Constants.DEFAULT_IMAGE }
            val image = rememberAsyncImagePainter(model = imageUrl, imageLoader = imageLoader)

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(dimen100)
                    .padding(start = spacing4)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .padding(spacing4)
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