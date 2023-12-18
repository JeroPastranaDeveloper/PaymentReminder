package com.pr.paymentreminder.presentation.login

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.LoginRegisterButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.register.RegisterActivity
import com.pr.paymentreminder.presentation.viewModels.LoginViewModel
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        checkLogin()
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue("cuentadepruebas@gmail.com")) }
            val passText = remember { mutableStateOf(TextFieldValue("123456Aa.")) }
            val wasEmailFieldFocused = remember { mutableStateOf(false) }
            val wasPassFieldFocused = remember { mutableStateOf(false) }

            initialValidations(emailText, passText)

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
            ) { viewModel.validateEmail() }

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

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.register) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            LoginRegisterButton(R.string.login) {
                if (isValidInput()) {
                    lifecycleScope.launch {
                        viewModel.login()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initialValidations(
        emailText: MutableState<TextFieldValue>,
        passText: MutableState<TextFieldValue>
    ) {
        viewModel.email = emailText.value.text
        viewModel.validateEmail()

        viewModel.password = passText.value.text
        viewModel.validatePassword()
    }

    private fun isValidInput(): Boolean {
        val isEmailValid = viewModel.validateEmail()
        val isPasswordValid = viewModel.validatePassword()

        return isEmailValid && isPasswordValid
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoginSuccessful.collect { isSuccessful ->
                    if (isSuccessful) {
                        startActivity(Intent(this@LoginActivity, PaymentReminderActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}