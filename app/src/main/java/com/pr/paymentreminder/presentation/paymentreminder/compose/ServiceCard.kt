package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.dimen100
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ServiceCard(
    service: Service,
    onClick: () -> Unit,
    dismissState: DismissState,
    deleteService: (String) -> Unit
) {
    val context = LocalContext.current
    val dismissThresholds = { _: DismissDirection -> FractionalThreshold(0.2f) }

    SwipeToDismiss(
        state = dismissState,
        dismissThresholds = dismissThresholds,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing8)
                    .clip(RoundedCornerShape(dimen16))
                    .background(Color.Red),
                content = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = spacing16)
                            .align(Alignment.CenterEnd)
                    )
                }
            )
        },
        directions = setOf(DismissDirection.EndToStart)
    ) {
        deleteService(service.id)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing8),
            onClick = onClick
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                /*val imageUri = service.image?.let { Uri.parse(it) }
                if (imageUri != null) {
                    val painter = rememberAsyncImagePainter(model = imageUri)

                    Image(
                        painter = painter,
                        contentDescription = emptyString(),
                        modifier = Modifier
                            .size(dimen100)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Fit,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = emptyString(),
                        modifier = Modifier
                            .width(dimen100)
                            .height(dimen100)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Fit,
                    )
                }*/

                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        add(SvgDecoder.Factory())
                    }
                    .build()

                val imageUrl = service.image.takeIf { !it.isNullOrEmpty() }.orElse{ Constants.DEFAULT_IMAGE }
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
}

/*when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    is AsyncImagePainter.State.Error -> {
                        Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                    }
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = emptyString(),
                            modifier = Modifier
                                .width(dimen100)
                                .height(dimen100)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Fit,
                        )
                    }

                    AsyncImagePainter.State.Empty -> {
                        Toast.makeText(LocalContext.current, "Empty", Toast.LENGTH_SHORT).show()
                    }
                }*/