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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.lifecycleScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.EmailField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.LoginRegisterButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassRepeatField
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

            EmailField(
                emailText = emailText.value,
                onEmailTextChange = { emailText.value = it },
                wasEmailFieldFocused = wasEmailFieldFocused.value,
                onEmailFieldFocusChange = { wasEmailFieldFocused.value = it },
                emailHelper = viewModel.emailHelperText,
                onEmailValidation = {
                    viewModel.validateEmail(emailText.value.text, getString(R.string.invalid_email))
                }
            )
            PassField(
                passText = passText.value,
                onPassTextChange = { passText.value = it },
                wasPassFieldFocused = wasPassFieldFocused.value,
                onPassFieldFocusChange = { wasPassFieldFocused.value = it },
                passHelper = viewModel.passHelperText,
                onPassValidation = {
                    viewModel.validatePassword(
                        passText.value.text,
                        getString(R.string.invalid_pass)
                    )
                }
            )

            PassRepeatField(
                repeatPassText = repeatPassText.value,
                onRepeatPassTextChange = { repeatPassText.value = it },
                wasPassFieldFocused = wasRepeatPassFieldFocused.value,
                onPassFieldFocusChange = { wasRepeatPassFieldFocused.value = it },
                passHelper = viewModel.repeatPassHelperText,
                onPassValidation = {
                    viewModel.validatePasswordMatch(
                        repeatPassText.value.text,
                        passText.value.text,
                        getString(R.string.passwords_do_not_match)
                    )
                }
            )

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.login) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }

            LoginRegisterButton(R.string.register) {
                if (isValidInput(emailText, passText, repeatPassText)) {
                    lifecycleScope.launch {
                        viewModel.register(emailText.value.text, passText.value.text)
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }

            CheckRegister()
        }
    }

    private fun isValidInput(
        emailText: MutableState<TextFieldValue>,
        passText: MutableState<TextFieldValue>,
        repeatPassText: MutableState<TextFieldValue>
    ): Boolean {
        val isEmailValid = viewModel.validateEmail(emailText.value.text, getString(R.string.invalid_email))
        val isPasswordValid = viewModel.validatePassword(passText.value.text, getString(R.string.invalid_pass))
        val isPasswordMatch = viewModel.validatePasswordMatch(repeatPassText.value.text, passText.value.text, getString(R.string.passwords_do_not_match))

        return isEmailValid && isPasswordValid && isPasswordMatch
    }

    @Composable
    private fun CheckRegister() {
        viewModel.isRegisterSuccessful.observeAsState().value?.let { isRegisterSuccessful ->
            if (isRegisterSuccessful) {
                startActivity(Intent(this@RegisterActivity, PaymentReminderActivity::class.java))
                finish()
            }
        }
    }
}