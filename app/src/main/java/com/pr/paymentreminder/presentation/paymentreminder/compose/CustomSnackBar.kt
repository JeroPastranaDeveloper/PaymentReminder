package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.CustomSnackBarConfig
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.ui.theme.dimen14
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.spacing10
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing18
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing6
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun CustomSnackBar(
    config: CustomSnackBarConfig,
    onClick: () -> Unit = {}
) {
    Box(
        Modifier
            .wrapContentWidth()
            .background(
                when (config.type) {
                    CustomSnackBarType.CREATE -> Color.Green
                    CustomSnackBarType.DELETE -> Color.Red
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
                .padding(start = spacing8, end = spacing16)
                .align(Al),
        ) {
            Spacer(modifier = Modifier.width(spacing18))
            Image(
                modifier = Modifier.size(dimen14),
                painter = painterResource(id = config.image),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(spacing10))
            Text(text = config.text, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onClick, modifier = Modifier.padding(vertical = spacing4)) {
                Text(text = "Deshacer")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SnackBarPreview() {
    CustomSnackBar(config = CustomSnackBarConfig("holi",  R.drawable.logo, CustomSnackBarType.DELETE))
}
