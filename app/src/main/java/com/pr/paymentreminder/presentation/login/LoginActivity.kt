package com.pr.paymentreminder.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue()) }
            val emailHelper by viewModel.emailHelperText.observeAsState()
            val wasEmailFieldFocused = remember { mutableStateOf(false) }

            val passText = remember { mutableStateOf(TextFieldValue()) }
            val passHelper by viewModel.passHelperText.observeAsState()
            val wasPassFieldFocused = remember { mutableStateOf(false) }

            OutlinedTextField(
                value = emailText.value,
                onValueChange = { emailText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16)
                    .onFocusChanged {
                        if (wasEmailFieldFocused.value && !it.isFocused) {
                            viewModel.validateEmail(emailText.value.text)
                        }
                        wasEmailFieldFocused.value = it.isFocused
                    },
                label = { Text(text = stringResource(id = R.string.email)) },
                isError = !emailHelper.isNullOrEmpty(),
                singleLine = true
            )

            if (!emailHelper.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.invalid_email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = spacing20, end = spacing16, bottom = spacing16),
                    color = Color.Red
                )
            }

            OutlinedTextField(
                value = passText.value,
                onValueChange = { passText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16)
                    .onFocusChanged {
                        if (wasPassFieldFocused.value && !it.isFocused) {
                            viewModel.validatePassword(passText.value.text)
                        }
                        wasPassFieldFocused.value = it.isFocused
                    },
                label = { Text(text = stringResource(id = R.string.password)) },
                isError = !passHelper.isNullOrEmpty(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (!passHelper.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.invalid_pass),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = spacing20, end = spacing16, bottom = spacing16),
                    color = Color.Red
                )
            }

            Button(
                onClick = {
                    if (viewModel.validateEmail(emailText.value.text) && viewModel.validatePassword(passText.value.text)) {
                        lifecycleScope.launch {
                            viewModel.login(emailText.value.text, passText.value.text)
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16)
            ) {
                Text(text = stringResource(id = R.string.login))
            }

            viewModel.isLoginSuccessful.observeAsState().value?.let { isLoginSuccessful ->
                if (isLoginSuccessful) {
                    startActivity(Intent(this@LoginActivity, PaymentReminderActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, R.string.error_login, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}