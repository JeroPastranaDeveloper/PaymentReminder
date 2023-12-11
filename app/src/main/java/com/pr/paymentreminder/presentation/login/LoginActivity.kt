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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pr.paymentreminder.presentation.paymentreminder.compose.EmailField
import androidx.lifecycle.lifecycleScope
import com.pr.paymentreminder.R
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
        viewModel.checkIfUserIsAuthenticated()
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

            Spacer(modifier = Modifier.height(dimen16))

            EmailField(
                emailText = emailText.value,
                onEmailTextChange = { emailText.value = it },
                wasEmailFieldFocused = wasEmailFieldFocused.value,
                onEmailFieldFocusChange = { wasEmailFieldFocused.value = it },
                emailHelper = viewModel.emailHelperText,
                onEmailValidation = { viewModel.validateEmail(emailText.value.text, getString(R.string.invalid_email)) }
            )

            PassField(
                passText = passText.value,
                onPassTextChange =  { passText.value = it },
                wasPassFieldFocused = wasPassFieldFocused.value,
                onPassFieldFocusChange = { wasPassFieldFocused.value = it },
                passHelper = viewModel.passHelperText,
                onPassValidation = { viewModel.validatePassword(passText.value.text, getString(R.string.invalid_pass)) }
            )

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.register) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            LoginRegisterButton(R.string.login) {
                if (isValidInput(emailText, passText)) {
                    lifecycleScope.launch {
                        viewModel.login(emailText.value.text, passText.value.text)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }

            CheckLogin()
        }
    }

    private fun isValidInput(
        emailText: MutableState<TextFieldValue>,
        passText: MutableState<TextFieldValue>
    ): Boolean {
        val isEmailValid = viewModel.validateEmail(emailText.value.text, getString(R.string.invalid_email))
        val isPasswordValid = viewModel.validatePassword(passText.value.text, getString(R.string.invalid_pass))

        return isEmailValid && isPasswordValid
    }

    @Composable
    private fun CheckLogin() {
        viewModel.isLoginSuccessful.observeAsState().value?.let { isLoginSuccessful ->
            if (isLoginSuccessful) {
                startActivity(Intent(this@LoginActivity, PaymentReminderActivity::class.java))
                finish()
            }
        }
    }
}