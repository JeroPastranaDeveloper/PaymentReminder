package com.pr.paymentreminder.presentation.register

import android.content.Intent
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
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.base.addRepeatingJob
import com.pr.paymentreminder.data.model.DefaultTextFieldParams
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.DefaultTextField
import com.pr.paymentreminder.presentation.paymentreminder.compose.ImageLogo
import com.pr.paymentreminder.presentation.paymentreminder.compose.PasswordField
import com.pr.paymentreminder.presentation.paymentreminder.compose.RegisterLoginButton
import com.pr.paymentreminder.presentation.paymentreminder.compose.UnderlinedText
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewModel
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    @Composable
    override fun ComposableContent() {
        addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect(::handleAction) }
        val state by viewModel.state.collectAsState(UiState())
        Content(state)
    }

    private fun handleAction(action: UiAction) {
        when (action) {
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

            DefaultTextField(
                DefaultTextFieldParams(
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

            PasswordField(
                DefaultTextFieldParams(
                    text = repeatPassText.value,
                    onTextChange = {
                        repeatPassText.value = it
                        viewModel.sendIntent(
                            UiIntent.ValidatePasswordValidation(
                                passText.value.text,
                                it.text
                            )
                        )
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
                viewModel.sendIntent(
                    UiIntent.CheckData(
                        emailText.value.text,
                        passText.value.text,
                        repeatPassText.value.text
                    )
                )
            }
        }
    }

    private fun goLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }
}