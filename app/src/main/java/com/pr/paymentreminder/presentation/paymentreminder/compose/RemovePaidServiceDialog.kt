package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun RemovePaidServiceDialog(
    titleText: String,
    bodyText: String,
    onRemove: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        title = { Text(text = titleText)},
        text = { Text(text = bodyText)},
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(
                onClick = {
                    onRemove()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}