package com.pr.identificationmodule.presentation.login

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
        data class ValidateEmail(val email: String) : UiIntent()
        data class ValidatePassword(val password: String) : UiIntent()
        data class DoLogin(val email: String, val password: String) : UiIntent()
        data object GoRegister : UiIntent()
    }

    sealed class UiAction {
        data object Login : UiAction()
        data object GoRegister : UiAction()
    }
}