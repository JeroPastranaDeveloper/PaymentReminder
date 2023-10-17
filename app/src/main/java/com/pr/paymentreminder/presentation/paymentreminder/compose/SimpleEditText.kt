package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pr.paymentreminder.ui.theme.dimen0
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen8
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.textSize1
import com.pr.paymentreminder.ui.theme.textSize12
import com.pr.paymentreminder.ui.theme.textSize14

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Stable
fun DefaultEditText(
    value: String,
    labelText: String = emptyString(),
    focusRequester: FocusRequester,
    isFocused: Boolean,
    error: String? = null,
    isEditable: Boolean = true,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) }
            .indicatorLine(
                enabled = enabled,
                isError = error != null,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Gray
                ),
                interactionSource = interactionSource,
                focusedIndicatorLineThickness = dimen1,
                unfocusedIndicatorLineThickness = dimen1
            ),
        enabled = enabled,
        readOnly = !isEditable,
        textStyle = if (enabled) TextStyle(fontSize = textSize14, color = Color.Black) else TextStyle(fontSize = textSize14, color = Color.Gray),
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions {
            keyboardController?.hide()
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                label = {
                    EditTextLabel(
                        value = value,
                        labelText = labelText,
                        isFocused = isFocused,
                        enabled = enabled,
                        error = error,
                    )
                },
                trailingIcon = trailingIcon,
                enabled = enabled,
                isError = error != null,
                singleLine = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red
                ),
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = dimen0,
                    end = dimen0,
                    bottom = dimen8,
                    top = dimen8
                )
            )
        }
    )
}

@Composable
fun EditTextLabel(
    value: String,
    labelText: String,
    isFocused: Boolean,
    enabled: Boolean,
    error: String?
) {
    Column {
        Text(
            text = if (value.isNotEmpty() || isFocused) labelText else emptyString(),
            style = when {
                isFocused && error == null -> TextStyle(fontSize = textSize12, color = Color.Gray)
                error != null -> TextStyle(fontSize = textSize12, color = Color.Red)
                !enabled  -> TextStyle(fontSize = textSize12, color = Color.Gray)
                else -> TextStyle(fontSize = textSize12, color = Color.Gray)
            }
        )

        //Spacer don't work, this do the trick to add extra padding.
        Text(modifier = Modifier.padding(0.dp), text = "", fontSize = textSize1)
    }
}