package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.pr.paymentreminder.R
import androidx.compose.ui.res.stringResource

@Composable
fun CustomDialog(
    titleText: String,
    bodyText: String,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        title = { Text(text = titleText)},
        text = { Text(text = bodyText)},
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept()
                }
            ) {
                Text(stringResource(id = R.string.accept_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(stringResource(id = R.string.cancel_button))
            }
        }
    )
}