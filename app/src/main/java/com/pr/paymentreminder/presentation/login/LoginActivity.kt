package com.pr.paymentreminder.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.HelperText
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.LoginRegisterButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.register.RegisterActivity
import com.pr.paymentreminder.presentation.viewModels.LoginNewViewModel
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.LoginViewModel
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20
import com.pr.paymentreminder.ui.theme.spacing8
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginNewViewModel by viewModels()

    private fun handleAction(action: UiAction) {
        when(action) {
            is UiAction.Login -> doLogin()
        }
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
            val wasEmailFieldFocused = remember { mutableStateOf(false) }
            val wasPassFieldFocused = remember { mutableStateOf(false) }

            initialValidations(emailText, passText)

            Spacer(modifier = Modifier.height(dimen16))

            /*DefaultTextField(
                DefaultTextFieldParams(
                    text = emailText.value,
                    onTextChange = {
                        emailText.value = it
                        viewModel.sendIntent(UiIntent.ValidateEmail(it.text))
                    },
                    wasTextFieldFocused = wasEmailFieldFocused.value,
                    onTextFieldFocusChange = { wasEmailFieldFocused.value = it },
                    placeHolder = stringResource(R.string.email),
                    textHelper = state.email,
                    textHelperText = stringResource(id = R.string.invalid_email)
                )
            ) { viewModel.sendIntent(UiIntent.ValidateEmail) }*/

            TextField(
                value = emailText.value,
                onValueChange = {
                    emailText.value = it
                    viewModel.sendIntent(UiIntent.ValidateEmail(it.text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16, vertical = spacing8)
                    .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                    .onFocusChanged {
                        if (wasEmailFieldFocused.value && !it.isFocused) {
                            viewModel.sendIntent(UiIntent.ValidateEmail(emailText.value.text))
                        }
                        wasEmailFieldFocused.value = it.isFocused
                    },
                label = { Text(text = stringResource(id = R.string.email)) },
                isError = !state.emailHelperText.isNullOrEmpty(),
                singleLine = true
            )

            /*hasHelperText?.let {
                HelperText(textHelperText)
            }*/

          /* PassField(
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
           ) { viewModel.validatePassword() }*/

            TextField(
                value = passText.value,
                onValueChange = {
                    passText.value = it
                    viewModel.sendIntent(UiIntent.ValidatePassword(it.text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16, vertical = spacing8)
                    .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                    .onFocusChanged {
                        if (wasPassFieldFocused.value && !it.isFocused) {
                            viewModel.sendIntent(UiIntent.ValidatePassword(emailText.value.text))
                        }
                        wasPassFieldFocused.value = it.isFocused
                    },
                label = { Text(text = stringResource(id = R.string.email)) },
                isError = !state.passwordHelperText.isNullOrEmpty(),
                singleLine = true
            )

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.register) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

            LoginRegisterButton(R.string.login) {
                initialValidations(emailText, passText)
                if (isValidInput()) {
                    lifecycleScope.launch {
                        viewModel.sendIntent(UiIntent.DoLogin)
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
        viewModel.sendIntent(UiIntent.ValidateEmail(emailText.value.text))
        viewModel.sendIntent(UiIntent.ValidatePassword(passText.value.text))
    }

    private fun isValidInput(): Boolean {
        val isEmailValid = !viewModel.state.value.emailHelperText.isNullOrEmpty()
        val isPasswordValid = !viewModel.state.value.passwordHelperText.isNullOrEmpty()

        return isEmailValid && isPasswordValid
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (viewModel.state.value.isLoginSuccessful) {
                    viewModel.sendIntent(UiIntent.DoLogin)
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