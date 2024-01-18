package com.pr.paymentreminder.presentation.viewModels.login

import com.pr.paymentreminder.base.BaseViewContract

class LoginViewContract : BaseViewContract() {
    data class UiState(
        val hasEmailHelperText: Boolean = true,
        val hasPasswordHelperText: Boolean = false,
        val isLoginSuccessful: Boolean = false
    )

    sealed class UiIntent {
        data class ValidateEmail(val email: String) : UiIntent()
        data class ValidatePassword(val password: String) : UiIntent()
        data class DoLogin(val email: String, val password: String) : UiIntent()
        data object AutoLogin : UiIntent()
        data object GoRegister : UiIntent()
    }

    sealed class UiAction {
        data object Login : UiAction()
        data object GoRegister : UiAction()
    }
}