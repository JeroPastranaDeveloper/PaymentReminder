package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun TopToast(
    modifier: Modifier = Modifier,
    alertConfig: TopToastAlertConfig
) {
    Box(
        Modifier
            .wrapContentWidth()
            .then(modifier)
            .background(
                when (alertConfig.type) {
                    ToastType.Success -> Color.Green
                    ToastType.Error -> Color.Red
                },
                shape = RoundedCornerShape(dimen8)
            )
    ) {
        Row(
            Modifier
                .padding(start = spacing6)
                .background(
                    Color.Black,
                    shape = RoundedCornerShape(topEnd = dimen8, bottomEnd = dimen8)
                )
                .fillMaxWidth()
                .padding(vertical = spacing24)
                .padding(start = spacing8, end = spacing16),
        ) {
            Spacer(modifier = Modifier.width(spacing18))
            Image(
                modifier = Modifier.size(dimen14),
                painter = painterResource(id = alertConfig.icon),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(spacing10))
            Text(text = alertConfig.text, color = Color.White)
        }
    }
}