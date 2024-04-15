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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.CustomSnackBarConfig
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.ui.theme.Visible
import com.pr.paymentreminder.ui.theme.dimen24
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.pastelBlue
import com.pr.paymentreminder.ui.theme.pastelGreen
import com.pr.paymentreminder.ui.theme.pastelRed
import com.pr.paymentreminder.ui.theme.semiBlack
import com.pr.paymentreminder.ui.theme.snackBarColor
import com.pr.paymentreminder.ui.theme.spacing10
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing18
import com.pr.paymentreminder.ui.theme.spacing4
import com.pr.paymentreminder.ui.theme.spacing6
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun CustomSnackBar(
    modifier: Modifier,
    config: CustomSnackBarConfig,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier
        .then(modifier)
        .padding(horizontal = spacing8)
        .background(
            when (config.type) {
                CustomSnackBarType.CREATE -> pastelGreen
                CustomSnackBarType.NONE -> pastelGreen
                CustomSnackBarType.UPDATE -> pastelBlue
                CustomSnackBarType.DELETE -> pastelRed
            },
            shape = RoundedCornerShape(dimen8)
        )
    ) {
        Row(
            Modifier
                .align(Alignment.Center)
                .padding(start = spacing6)
                .background(
                    snackBarColor,
                    shape = RoundedCornerShape(topEnd = dimen8, bottomEnd = dimen8)
                )
                .fillMaxWidth()
                .padding(start = spacing8, end = spacing16)
        ) {
            Spacer(modifier = Modifier.width(spacing18))
            Image(
                modifier = Modifier
                    .size(dimen24)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = config.image),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(spacing10))
            Text(
                text = config.text,
                color = semiBlack,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Visible(visible = config.type == CustomSnackBarType.DELETE) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { onClick() },
                    modifier = Modifier.padding(vertical = spacing4)
                ) {
                    Text(
                        text = stringResource(id = R.string.undo),
                        color = semiBlack
                    )
                }
            }
        }
    }
}

/*
@Composable
@Preview(showBackground = true)
private fun SnackBarPreview() {
    CustomSnackBar(
        config = CustomSnackBarConfig(
            R.drawable.baseline_delete_24,
            CustomSnackBarType.DELETE
        )
    )
}
*/
