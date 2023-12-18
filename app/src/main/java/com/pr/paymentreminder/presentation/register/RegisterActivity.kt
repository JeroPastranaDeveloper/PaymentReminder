package com.pr.paymentreminder.presentation.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.lifecycleScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.LoginRegisterButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.viewModels.RegisterViewModel
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                // .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue()) }
            val wasEmailFieldFocused = remember { mutableStateOf(false) }

            val passText = remember { mutableStateOf(TextFieldValue()) }
            val wasPassFieldFocused = remember { mutableStateOf(false) }

            val repeatPassText = remember { mutableStateOf(TextFieldValue()) }
            val wasRepeatPassFieldFocused = remember { mutableStateOf(false) }

            Spacer(modifier = Modifier.height(dimen16))

            DefaultTextField(
                DefaultTextFieldParams(
                    text = emailText.value,
                    onTextChange = {
                        emailText.value = it
                        viewModel.email = it.text
                    },
                    wasTextFieldFocused = wasEmailFieldFocused.value,
                    onTextFieldFocusChange = { wasEmailFieldFocused.value = it },
                    placeHolder = stringResource(R.string.email),
                    textHelper = viewModel.emailHelperText,
                    textHelperText = stringResource(id = R.string.invalid_email)
                )
            ) {
                viewModel.validateEmail()
            }

            PassField(
                DefaultTextFieldParams(
                    text = passText.value,
                    onTextChange = {
                        passText.value = it
                        viewModel.password = it.text
                    },
                    wasTextFieldFocused = wasPassFieldFocused.value,
                    onTextFieldFocusChange = { wasPassFieldFocused.value = it },
                    placeHolder = stringResource(R.string.password),
                    textHelper = viewModel.passHelperText,
                    textHelperText = stringResource(id = R.string.invalid_pass)
                )
            ) { viewModel.validatePassword() }

            PassField(
                DefaultTextFieldParams(
                    text = repeatPassText.value,
                    onTextChange = {
                        repeatPassText.value = it
                        viewModel.repeatPassword = it.text
                    },
                    wasTextFieldFocused = wasRepeatPassFieldFocused.value,
                    onTextFieldFocusChange = { wasRepeatPassFieldFocused.value = it },
                    placeHolder = stringResource(R.string.repeat_password),
                    textHelper = viewModel.repeatPassHelperText,
                    textHelperText = stringResource(id = R.string.passwords_do_not_match)
                )
            ) { viewModel.validatePasswordMatch() }

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.login) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }

            LoginRegisterButton(R.string.register) {
                if (isValidInput()) {
                    lifecycleScope.launch {
                        viewModel.register()
                        startActivity(Intent(this@RegisterActivity, PaymentReminderActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val isEmailValid = viewModel.validateEmail()
        val isPasswordValid = viewModel.validatePassword()
        val isPasswordMatch = viewModel.validatePasswordMatch()

        return isEmailValid && isPasswordValid && isPasswordMatch
    }
}