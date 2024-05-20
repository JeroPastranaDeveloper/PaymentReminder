package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.pr.paymentreminder.ui.theme.light_grey
import com.pr.paymentreminder.ui.theme.white

fun Modifier.placeholder(
    visible: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(percent = 12)
): Modifier = this.composed {
    placeholder(
        visible = visible,
        color = light_grey,
        highlight = PlaceholderHighlight.fade(white),
        shape = shape
    )
}