package com.pr.paymentreminder.presentation.login

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.ui.theme.emptyString

class LoginViewContract : BaseViewContract() {
    data class UiState(
        val email: String = emptyString(),
        val hasEmailHelperText: Boolean = false,
        val hasPasswordHelperText: Boolean = false,
        val isLoginSuccessful: Boolean = false,
        val isValidInput: Boolean = true,
        val password: String = emptyString()
    )

    sealed class UiIntent {
        data class CheckIsValidInput(val email: String, val password: String) : UiIntent()
        data class DoLogin(val email: String = emptyString(), val password: String = emptyString()) : UiIntent()
        data object GoRegister : UiIntent()
        data class ValidateEmail(val email: String) : UiIntent()
        data class ValidatePassword(val password: String) : UiIntent()
    }

    sealed class UiAction {
        data object DoBiometricAuthentication : UiAction()
        data object GoRegister : UiAction()
        data object Login : UiAction()
    }
}