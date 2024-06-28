package com.pr.paymentreminder.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pr.paymentreminder.R

val semiBlack @Composable get() = colorResource(id = R.color.semi_black)
val white @Composable get() = colorResource(id = R.color.white)
val light_grey @Composable get() = colorResource(id = R.color.light_grey)
val green @Composable get() = colorResource(id = R.color.green)
val red @Composable get() = colorResource(id = R.color.red)
val blue @Composable get() = colorResource(id = R.color.blue)
val snackBarColor @Composable get() = colorResource(id = R.color.snack_bar)

// Backgrounds
val backGroundColor @Composable get() = colorResource(id = R.color.background_color)
val chai @Composable get() = colorResource(id = R.color.chai)
val vanilla @Composable get() = colorResource(id = R.color.vanilla)
val almond @Composable get() = colorResource(id = R.color.almond)
val matcha @Composable get() = colorResource(id = R.color.matcha)
val pistacho @Composable get() = colorResource(id = R.color.pistacho)
val carob @Composable get() = colorResource(id = R.color.carob)
val brokenWhite @Composable get() = colorResource(id = R.color.broken_white)

// Pastel colors
val pastelBlue @Composable get() = colorResource(id = R.color.pastel_blue)
val pastelGreen @Composable get() = colorResource(id = R.color.pastel_green)
val pastelGrey @Composable get() = colorResource(id = R.color.pastel_grey)
val pastelMint @Composable get() = colorResource(id = R.color.pastel_mint)
val pastelPink @Composable get() = colorResource(id = R.color.pastel_pink)
val pastelPurple @Composable get() = colorResource(id = R.color.pastel_purple)
val pastelRed @Composable get() = colorResource(id = R.color.pastel_red)
val pastelSand @Composable get() = colorResource(id = R.color.pastel_sand)

@Composable
fun getPastelColors(): List<Color> = listOf(
    pastelRed,
    pastelPink,
    pastelBlue,
    pastelGrey,
    pastelGreen,
    pastelSand,
    pastelPurple,
    semiBlack,
    pastelMint
)

@Composable
fun getBackgroundColors(): List<Color> = listOf(
    matcha,
    almond,
    pistacho,
    chai,
    carob,
    vanilla
)
