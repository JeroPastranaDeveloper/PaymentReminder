package com.pr.identificationmodule.presentation.register

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.theme.emptyString

class RegisterViewContract : BaseViewContract() {
    data class UiState(
        val email: String = emptyString(),
        val password: String = emptyString(),
        val passwordValidation: String = emptyString(),
        val hasEmailHelperText: Boolean = false,
        val hasPasswordHelperText: Boolean = false,
        val hasPasswordValidationHelperText: Boolean = false,
        val isLoginSuccessful: Boolean = false
    )

    sealed class UiIntent {
        data class CheckData(val email: String?, val password: String?, val passwordValidation: String?) : UiIntent()
        data class ValidateEmail(val email: String?) : UiIntent()
        data class ValidatePassword(val password: String?) : UiIntent()
        data class ValidatePasswordValidation(val password: String?, val passwordValidation: String?) : UiIntent()
        data object GoLogin : UiIntent()
    }

    sealed class UiAction {
        data object Register : UiAction()
        data object GoLogin : UiAction()
    }
}