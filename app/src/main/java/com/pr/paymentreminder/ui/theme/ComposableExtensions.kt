package com.pr.paymentreminder.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun Visible(visible: Boolean, content: @Composable () -> Unit) {
    if (visible) content()
}