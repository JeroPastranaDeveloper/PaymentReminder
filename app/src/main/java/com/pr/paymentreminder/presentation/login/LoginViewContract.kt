package com.pr.paymentreminder.presentation.login

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.ui.theme.emptyString

class LoginViewContract : BaseViewContract() {
    data class UiState(
        val hasEmailHelperText: Boolean = false,
        val hasPasswordHelperText: Boolean = false,
        val email: String = emptyString(),
        val password: String = emptyString(),
        val isLoginSuccessful: Boolean = false
    )

    sealed class UiIntent {
        data object GoRegister : UiIntent()
        data class DoLogin(val email: String = emptyString(), val password: String = emptyString()) : UiIntent()
        data class ValidateEmail(val email: String) : UiIntent()
        data class ValidatePassword(val password: String) : UiIntent()
    }

    sealed class UiAction {
        data object DoBiometricAuthentication : UiAction()
        data object GoRegister : UiAction()
        data object Login : UiAction()
    }
}