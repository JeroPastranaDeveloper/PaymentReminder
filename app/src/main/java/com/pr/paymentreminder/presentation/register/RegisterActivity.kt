package com.pr.paymentreminder.presentation.register

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
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.data.model.EmailTextFieldParams
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.EmailTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.NewPassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.RegisterLoginButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.PassField
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewModel
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())
        setContent {
            Content(state)
        }
    }

    private fun handleAction(action: UiAction) {
        when(action) {
            UiAction.Register -> doRegister()
            UiAction.GoLogin -> goLogin()
        }
    }

    private fun doRegister() {
        startActivity(Intent(this@RegisterActivity, PaymentReminderActivity::class.java))
        finish()
    }

    @Composable
    private fun Content(state: UiState) {
        Column(
            modifier = Modifier
                .padding(spacing16)
                // .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue()) }

            val passText = remember { mutableStateOf(TextFieldValue()) }

            val repeatPassText = remember { mutableStateOf(TextFieldValue()) }

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

            NewPassField(
                EmailTextFieldParams(
                    text = repeatPassText.value,
                    onTextChange = {
                        repeatPassText.value = it
                        viewModel.sendIntent(UiIntent.ValidatePasswordValidation(passText.value.text, it.text))
                    },
                    placeHolder = stringResource(R.string.repeat_password),
                    hasHelperText = state.hasPasswordValidationHelperText,
                    textHelperText = stringResource(id = R.string.passwords_do_not_match)
                )
            )

            ImageLogo(R.drawable.logo_no_bg)

            Spacer(modifier = Modifier.weight(1f))

            UnderlinedText(text = R.string.login) {
                viewModel.sendIntent(UiIntent.GoLogin)
            }

            RegisterLoginButton(R.string.register) {
                if (isValidInput(state)) {
                    viewModel.sendIntent(UiIntent.Register(emailText.value.text, passText.value.text))
                } else {
                    Toast.makeText(this@RegisterActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun goLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

    private fun isValidInput(state: UiState): Boolean {
        val isEmailValid = state.hasEmailHelperText
        val isPasswordValid = state.hasPasswordHelperText
        val isPasswordValidationValid = state.hasPasswordValidationHelperText

        return isEmailValid && isPasswordValid && isPasswordValidationValid
    }
}