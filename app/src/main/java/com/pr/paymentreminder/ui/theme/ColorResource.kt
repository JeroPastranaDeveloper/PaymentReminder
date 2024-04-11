package com.pr.paymentreminder.ui.theme

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DoNotInline
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
@ReadOnlyComposable
fun colorResource(@ColorRes id: Int): Color {
    val context = LocalContext.current
    return if (Build.VERSION.SDK_INT >= 23) {
        ColorResourceHelper.getColor(context, id)
    } else {
        @Suppress("DEPRECATION")
        (Color(context.resources.getColor(id)))
    }
}

@RequiresApi(23)
private object ColorResourceHelper {
    @DoNotInline
    fun getColor(context: Context, @ColorRes id: Int): Color {
        return Color(context.resources.getColor(id, context.theme))
    }
}