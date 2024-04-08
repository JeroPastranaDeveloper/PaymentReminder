package com.pr.paymentreminder.theme

import androidx.compose.runtime.Composable

@Composable
fun Visible(visible: Boolean, content: @Composable () -> Unit) {
    if (visible) content()
}