package com.pr.paymentreminder.ui.theme

fun <T> Boolean.doIfTrue(block: () -> T): T? = if (this) block.invoke() else null

fun <T> Boolean.doIfFalse(block: () -> T): T? = if (!this) block.invoke() else null

fun Boolean?.orFalse() = this ?: false