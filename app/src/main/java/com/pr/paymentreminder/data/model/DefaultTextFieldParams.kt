package com.pr.paymentreminder.data.model

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.StateFlow

data class DefaultTextFieldParams(
    val text: TextFieldValue,
    val onTextChange: (TextFieldValue) -> Unit,
    val wasTextFieldFocused: Boolean,
    val onTextFieldFocusChange: (Boolean) -> Unit,
    val textHelper: StateFlow<String?>,
    val textHelperText: String,
    val placeHolder: String
)

data class EmailTextFieldParams(
    val text: TextFieldValue,
    val onTextChange: (TextFieldValue) -> Unit,
    val hasHelperText: Boolean,
    val textHelperText: String,
    val placeHolder: String
)