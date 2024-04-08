package com.pr.identificationmodule.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.pr.paymentreminder.theme.spacing8

@Composable
fun UnderlinedText(
    text: Int,
    onClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(stringResource(id = text))
            }
        },
        modifier = Modifier.padding(bottom = spacing8).clickable { onClick() },
        color = Color.Blue
    )
}