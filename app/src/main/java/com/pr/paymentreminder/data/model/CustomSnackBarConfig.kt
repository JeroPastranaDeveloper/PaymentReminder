package com.pr.paymentreminder.data.model

import androidx.annotation.DrawableRes

data class CustomSnackBarConfig(
    @DrawableRes val image: Int,
    val text: String,
    val type: CustomSnackBarType
)

enum class CustomSnackBarType {
    CREATE, NONE, UPDATE, DELETE
}