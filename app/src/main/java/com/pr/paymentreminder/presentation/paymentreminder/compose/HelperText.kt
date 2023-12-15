package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun HelperText(
    helperText: Int
) {
    Text(
        text = stringResource(id = helperText),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing20, end = spacing16, bottom = spacing8),
        color = Color.Red
    )
}