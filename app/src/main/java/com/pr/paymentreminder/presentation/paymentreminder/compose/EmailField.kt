package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing8

@Composable
fun EmailField(
    emailText: TextFieldValue,
    onEmailTextChange: (TextFieldValue) -> Unit,
    wasEmailFieldFocused: Boolean,
    onEmailFieldFocusChange: (Boolean) -> Unit,
    emailHelper: LiveData<String?>,
    onEmailValidation: (String) -> Unit
) {
    val emailHelperText by emailHelper.observeAsState(null)

    TextField(
        value = emailText,
        onValueChange = onEmailTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing16, vertical = spacing8)
            .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
            .onFocusChanged {
                if (wasEmailFieldFocused && !it.isFocused) {
                    onEmailValidation(emailText.text)
                }
                onEmailFieldFocusChange(it.isFocused)
            },
        label = { Text(text = stringResource(id = R.string.email)) },
        isError = !emailHelper.value.isNullOrEmpty(),
        singleLine = true
    )

    if (!emailHelperText.isNullOrEmpty()) {
        HelperText(R.string.invalid_email)
    }
}