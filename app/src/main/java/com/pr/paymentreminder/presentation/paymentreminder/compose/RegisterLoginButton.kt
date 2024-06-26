package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.spacing16

@Composable
fun RegisterLoginButton(
    text: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing16)
    ) {
        Text(text = stringResource(id = text))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginRegisterButtonPreview() {
    RegisterLoginButton(text = R.string.login) {}
}
