package com.pr.paymentreminder.androidVersions

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun hasS31(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
fun hasSV232(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2
}