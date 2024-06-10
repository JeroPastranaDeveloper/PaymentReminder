package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.ui.theme.spacing8


@Composable
fun PasswordField(
    params: DefaultTextFieldParams,
) {
    with(params) {
        val passwordVisibility = remember { mutableStateOf(false) }

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing8),
            label = { Text(text = placeHolder) },
            isError = hasHelperText,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        if (hasHelperText) HelperText(textHelperText)
    }
}