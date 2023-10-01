package com.pr.paymentreminder.ui.theme

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
@ReadOnlyComposable
fun textUnitOf(@DimenRes id: Int): TextUnit =
    dimensionResource(id = id).value.scaledSp()

@Composable
@ReadOnlyComposable
fun Float.scaledSp(): TextUnit {
    val value: Float = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize =  value / fontScale
        textSize.sp
    }
}