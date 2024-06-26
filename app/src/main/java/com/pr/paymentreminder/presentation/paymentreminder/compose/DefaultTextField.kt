package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun DefaultTextField(
    params: DefaultTextFieldParams
) {
    with(params) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing8),
            label = { Text(text = placeHolder) },
            isError = hasHelperText,
            singleLine = true,
            keyboardOptions = if (isEmail) KeyboardOptions(keyboardType = KeyboardType.Email) else KeyboardOptions.Default
        )

        if (hasHelperText) HelperText(textHelperText)
    }
}
