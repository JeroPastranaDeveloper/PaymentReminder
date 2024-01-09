package com.pr.paymentreminder.presentation.login

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.EmailTextFieldParams
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.EmailTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.RegisterLoginButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.NewPassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.register.RegisterActivity
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewModel
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiAction
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private fun handleAction(action: UiAction) {
        when(action) {
            UiAction.Login -> doLogin()
            UiAction.GoRegister -> goRegister()
        }
    }

    private fun goRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }

    private fun doLogin() {
        startActivity(Intent(this@LoginActivity, PaymentReminderActivity::class.java))
        finish()
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

            EmailTextField(
                EmailTextFieldParams(
                    text = emailText.value,
                    onTextChange = {
                        emailText.value = it
                        viewModel.sendIntent(UiIntent.ValidateEmail(it.text))
                    },
                    placeHolder = stringResource(R.string.email),
                    hasHelperText = state.hasEmailHelperText,
                    textHelperText = stringResource(id = R.string.invalid_email)
                )
            )

            NewPassField(
                EmailTextFieldParams(
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
                viewModel.sendIntent(UiIntent.DoLogin(emailText.value.text, passText.value.text))
                /*initialValidations(emailText, passText)
                if (isValidInput(state)) {
                    viewModel.sendIntent(UiIntent.DoLogin(emailText.value.text, passText.value.text))
                } else {
                    Toast.makeText(this@LoginActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }*/
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

    private fun isValidInput(state: UiState): Boolean {
        val isEmailValid = state.hasEmailHelperText
        val isPasswordValid = state.hasPasswordHelperText

        return isEmailValid && isPasswordValid
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (viewModel.state.value.isLoginSuccessful) {
                    viewModel.sendIntent(UiIntent.AutoLogin)
                }
            }
        }
    }

    @Composable
    override fun ComposableContent() {
        // installSplashScreen()
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        checkLogin()
        val state by viewModel.state.collectAsState(UiState())
        setContent {
            Content(state)
        }
    }
}