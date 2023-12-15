package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8
import kotlinx.coroutines.flow.StateFlow


@Composable
fun PassField(
    passText: TextFieldValue,
    onPassTextChange: (TextFieldValue) -> Unit,
    wasPassFieldFocused: Boolean,
    onPassFieldFocusChange: (Boolean) -> Unit,
    passHelper: StateFlow<String?>,
    onPassValidation: (String) -> Unit
) {
    val passHelperText by passHelper.collectAsState(null)
    val passwordVisibility = remember { mutableStateOf(false) }

    TextField(
        value = passText,
        onValueChange = onPassTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing16, vertical = spacing8)
            .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
            .onFocusChanged {
                if (wasPassFieldFocused && !it.isFocused) {
                    onPassValidation(passText.text)
                }
                onPassFieldFocusChange(it.isFocused)
            },
        label = { Text(text = stringResource(id = R.string.password)) },
        isError = !passHelper.value.isNullOrEmpty(),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                Icon(
                    imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = emptyString()
                )
            }
        }
    )

    passHelperText?.let {
        HelperText(it)
    }
}