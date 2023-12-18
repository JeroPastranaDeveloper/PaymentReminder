package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun DefaultTextField(
    params: DefaultTextFieldParams,
    onTextValidation: (String) -> Unit
) {
    with(params) {
        val hasHelperText by textHelper.collectAsState(null)

        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                .onFocusChanged {
                    if (wasTextFieldFocused && !it.isFocused) {
                        onTextValidation(text.text)
                    }
                    onTextFieldFocusChange(it.isFocused)
                },
            label = { Text(text = placeHolder) },
            isError = !textHelper.value.isNullOrEmpty(),
            singleLine = true
        )

        hasHelperText?.let {
            HelperText(textHelperText)
        }
    }
}