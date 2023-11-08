package com.pr.paymentreminder.ui.theme

import androidx.annotation.DimenRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
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

fun emptyString(): String = ""

fun <T> T?.orElse(result: () -> T): T = this ?: result()

@Composable
fun onReleaseClick(
    onClick: () -> Unit
) : MutableInteractionSource = remember { MutableInteractionSource() }
    .also { interactionSource ->
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect {
                if (it is PressInteraction.Release) {
                    onClick()
                }
            }
        }
    }