package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.dimen10
import com.pr.paymentreminder.ui.theme.dimen200
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing16

@Composable
fun ServiceDialog(
    service: Service?,
    onClick: () -> Unit,
    onEdit: () -> Unit
) {
    val context = LocalContext.current
    val imageUrl = service?.image.takeIf { !it.isNullOrEmpty() }.orElse{ Constants.DEFAULT_IMAGE }
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    val image = rememberAsyncImagePainter(model = imageUrl, imageLoader = imageLoader)

    service?.let {
        Dialog(onDismissRequest = onClick) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.White
            ) {
                Box {
                    Column(
                        modifier = Modifier.padding(spacing16),
                        verticalArrangement = Arrangement.spacedBy(dimen10)
                    ) {
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimen200)
                                .clip(RoundedCornerShape(percent = 5)),
                            contentScale = ContentScale.Fit
                        )
                        Text(text = stringResource(id = R.string.dialog_name, service.name))
                        Text(text = stringResource(id = R.string.dialog_date, service.date))
                        Text(text = stringResource(id = R.string.dialog_type, service.type))
                        Text(text = stringResource(id = R.string.dialog_pryce, service.price + "€"))
                    }
                    IconButton(onClick = onEdit, modifier = Modifier.align(Alignment.BottomEnd)) {
                        Icon(Icons.Filled.Edit, contentDescription = null)
                    }
                }
            }
        }
    }
}
