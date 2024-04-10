package com.pr.paymentreminder.data.model

import androidx.annotation.DrawableRes

data class CustomSnackBarConfig(
    val text: String,
    @DrawableRes val image: Int,
    val type: CustomSnackBarType
)

enum class CustomSnackBarType {
    DELETE, CREATE
}