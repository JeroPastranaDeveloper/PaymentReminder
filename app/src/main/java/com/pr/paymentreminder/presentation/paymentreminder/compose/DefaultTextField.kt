package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun DefaultTextField(
    params: DefaultTextFieldParams
) {
    with(params) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
            label = { Text(text = placeHolder) },
            isError = hasHelperText,
            singleLine = true
        )

        if (hasHelperText) HelperText(textHelperText)
    }
}