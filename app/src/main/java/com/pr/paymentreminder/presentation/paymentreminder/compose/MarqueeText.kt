package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import com.pr.paymentreminder.ui.theme.emptyString

// TODO: HACER QUE EL TEXTO NO SE SUPERPONGA A LA IMAGEN
@Composable
fun MarqueeText(
    text: String,
    modifier: Modifier = Modifier,
    duration: Int = 10000
) {
    val infiniteTransition = rememberInfiniteTransition(label = emptyString())
    val offset by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = emptyString()
    )

    Box(modifier = modifier) {
        Text(
            text = text,
            maxLines = 1,
            modifier = Modifier.graphicsLayer { translationX = offset * (text.length * 15)},
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
