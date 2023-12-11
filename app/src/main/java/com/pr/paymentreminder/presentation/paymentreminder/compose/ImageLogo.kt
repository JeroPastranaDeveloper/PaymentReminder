package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pr.paymentreminder.ui.theme.spacing72

@Composable
fun ImageLogo(
    image: Int
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().padding(vertical = spacing72)
    )
}