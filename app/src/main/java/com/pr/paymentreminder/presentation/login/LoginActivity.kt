package com.pr.paymentreminder.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Content() {
        Column(modifier = Modifier
            .padding(spacing16)
            .fillMaxSize()) {
            (if (viewModel.emailHelperText.isNullOrEmpty()) null else stringResource(id = R.string.invalid_email))?.let {
                TextFields(
                    helperText = it,
                    onEmailChanged = { email ->
                        viewModel.
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TextFields(
        helperText: String,
        onEmailChanged: (String) -> Unit
    ) {
        val text by remember { mutableStateOf(emptyString()) }

        TextField(
            value = text,
            onValueChange = {
                viewModel.validateEmail(it)
            }
        )
    }
}