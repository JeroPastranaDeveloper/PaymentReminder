package com.pr.paymentreminder.ui.theme

import androidx.compose.runtime.Composable

fun <T> Boolean.doIfTrue(block: () -> T): T? {
    return if (this) block.invoke() else null
}

fun <T> Boolean.doIfFalse(block: () -> T): T? {
    return if (!this) block.invoke() else null
}

@Composable
fun <T> Boolean.doComposableIfTrue(block: @Composable () -> T): T? {
    return if (this) block.invoke() else null
}

fun Boolean?.orFalse() = this ?: false