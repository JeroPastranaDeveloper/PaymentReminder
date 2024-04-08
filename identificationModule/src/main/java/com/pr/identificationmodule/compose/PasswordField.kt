package com.pr.identificationmodule.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.pr.paymentreminder.compose.HelperText
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.theme.dimen1
import com.pr.paymentreminder.theme.dimen4
import com.pr.paymentreminder.theme.emptyString
import com.pr.paymentreminder.theme.spacing8


@Composable
fun PasswordField(
    params: DefaultTextFieldParams,
) {
    with(params) {
        val passwordVisibility = remember { mutableStateOf(false) }

        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4)),
            label = { Text(text = placeHolder) },
            isError = hasHelperText,
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Filled.Favorite else Icons.Filled.Edit,
                        contentDescription = emptyString()
                    )
                }
            }
        )

        if (hasHelperText) HelperText(textHelperText)
    }
}