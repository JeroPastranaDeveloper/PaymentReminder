package com.pr.paymentreminder.presentation.login

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiAction
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesHandler: PreferencesHandler,
    private val emailValidator: EmailValidator
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.CheckIsValidInput -> checkIsValidInput(intent.email, intent.password)
            is UiIntent.DoLogin -> login(intent.email, intent.password)
            UiIntent.GoRegister -> dispatchAction(UiAction.GoRegister)
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
        }
    }

    init {
        checkLogin()
    }

    private fun checkIsValidInput(email: String, password: String) {
        validateEmail(email)
        validatePassword(password)
        if (!state.value.hasEmailHelperText && !state.value.hasPasswordHelperText) {
            setState { copy(isValidInput = true) }
            login(email, password)
        } else {
            setState { copy(isValidInput = false) }
        }
    }

    private fun login(email: String, password: String) {
        val loginEmail = email.ifEmpty { preferencesHandler.email.orEmpty() }
        val loginPassword = password.ifEmpty { preferencesHandler.password.orEmpty() }

        viewModelScope.launch {
            val isLoginSuccessful = loginUseCase(loginEmail, loginPassword)

            if (isLoginSuccessful && email.isNotEmpty() && password.isNotEmpty()) {
                preferencesHandler.hasToLogin = true
                preferencesHandler.email = loginEmail
                preferencesHandler.password = loginPassword

                dispatchAction(UiAction.Login)
            } else setState { copy(isValidInput = false) }
        }
    }

    private fun checkLogin() {
        if (preferencesHandler.hasToLogin) {
            dispatchAction(UiAction.DoBiometricAuthentication)
        }
    }

    private fun validateEmail(email: String) {
        val isEmailInvalid = emailValidator.validate(email)
        setState {
            copy(hasEmailHelperText = isEmailInvalid)
        }
    }

    private fun validatePassword(password: String) {
        val isPasswordInvalid = password.isEmpty() || password.length < 8 || !password.matches(".*[0-9].*".toRegex())
        setState {
            copy(hasPasswordHelperText = isPasswordInvalid)
        }
    }
}
