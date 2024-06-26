package com.pr.paymentreminder.presentation.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.authentication.BiometricAuthenticator
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiAction
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.PasswordField
import com.pr.paymentreminder.presentation.paymentreminder.compose.RegisterLoginButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.register.RegisterActivity
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var biometricAuthenticator: BiometricAuthenticator

    @Composable
    override fun ComposableContent() {
        // installSplashScreen()

        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())

        LaunchedEffect(state.isValidInput) {
            if (!state.isValidInput) {
                Toast.makeText(this@LoginActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
            }
        }

        Content(state)
    }

    private fun handleAction(action: UiAction) {
        when(action) {
            UiAction.DoBiometricAuthentication -> doBiometricAuth()
            UiAction.GoRegister -> goRegister()
            UiAction.Login -> doLogin()
        }
    }

    private fun doBiometricAuth() {
        biometricAuthenticator = BiometricAuthenticator(this)
        biometricAuthenticator.authenticate(
            onSuccess = { viewModel.sendIntent(UiIntent.DoLogin()) }
        )
    }

    @Composable
    private fun Content(state: UiState) {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue("cuentadepruebas@gmail.com")) }
            val passText = remember { mutableStateOf(TextFieldValue("123456Aa.")) }

            initialValidations(emailText, passText)

            Spacer(modifier = Modifier.height(dimen16))

            DefaultTextField(
                DefaultTextFieldParams(
                    text = emailText.value,
                    onTextChange = {
                        emailText.value = it
                        viewModel.sendIntent(UiIntent.ValidateEmail(it.text))
                    },
                    placeHolder = stringResource(R.string.email),
                    hasHelperText = state.hasEmailHelperText,
                    textHelperText = stringResource(id = R.string.invalid_email),
                    isEmail = true
                )
            )

            PasswordField(
                DefaultTextFieldParams(
                    text = passText.value,
                    onTextChange = {
                        passText.value = it
                        viewModel.sendIntent(UiIntent.ValidatePassword(it.text))
                    },
                    placeHolder = stringResource(R.string.password),
                    hasHelperText = state.hasPasswordHelperText,
                    textHelperText = stringResource(id = R.string.invalid_pass)
                )
            )

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.register) {
                viewModel.sendIntent(UiIntent.GoRegister)
            }

            RegisterLoginButton(R.string.login) {
                viewModel.sendIntent(UiIntent.CheckIsValidInput(emailText.value.text, passText.value.text))
            }
        }
    }

    private fun initialValidations(
        emailText: MutableState<TextFieldValue>,
        passText: MutableState<TextFieldValue>
    ) {
        viewModel.sendIntent(UiIntent.ValidateEmail(emailText.value.text))
        viewModel.sendIntent(UiIntent.ValidatePassword(passText.value.text))
    }

    private fun goRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }

    private fun doLogin() {
        startActivity(Intent(this@LoginActivity, PaymentReminderActivity::class.java))
        finish()
    }
}