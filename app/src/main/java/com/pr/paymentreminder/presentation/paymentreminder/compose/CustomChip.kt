package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.semiBlack
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing8
import com.pr.paymentreminder.ui.theme.white

@Composable
fun CustomChip(
    title: String,
    onClick: () -> Unit,
    selected: Boolean
) {
    val colors = if (selected) semiBlack else white
    val contentColors = if (selected) white else semiBlack

    Surface(
        modifier = Modifier
            .padding(end = spacing4)
            .border(dimen1, semiBlack, RoundedCornerShape(dimen8)),
        shape = RoundedCornerShape(spacing8),
        color = colors,
        contentColor = contentColors
    ) {
        Text(title, modifier = Modifier.padding(spacing8).clickable { onClick() })
    }
}
