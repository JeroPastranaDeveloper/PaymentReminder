package com.pr.paymentreminder.data.model

import androidx.compose.ui.text.input.TextFieldValue

data class DefaultTextFieldParams(
    val text: TextFieldValue,
    val onTextChange: (TextFieldValue) -> Unit,
    val hasHelperText: Boolean,
    val textHelperText: String,
    val placeHolder: String
)