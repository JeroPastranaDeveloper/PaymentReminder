package com.pr.paymentreminder.data.model

import androidx.annotation.DrawableRes
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.emptyString
import java.util.UUID

data class CustomToastInfo(
    private val id: String = UUID.randomUUID().toString(),
    val message: String = emptyString(),
    @DrawableRes val icon: Int = R.drawable.logo
)