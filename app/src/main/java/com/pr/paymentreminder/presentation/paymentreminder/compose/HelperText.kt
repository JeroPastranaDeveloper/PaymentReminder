package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20

@Composable
fun HelperText(
    helperText: String
) {
    Text(
        text = helperText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing20, end = spacing16),
        color = Color.Red
    )
}