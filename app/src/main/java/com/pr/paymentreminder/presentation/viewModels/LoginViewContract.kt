package com.pr.paymentreminder.presentation.viewModels

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.ui.theme.emptyString

class LoginViewContract : BaseViewContract() {
    data class UiState(
        val email: String = emptyString(),
        val password: String = emptyString(),
        val emailHelperText: String? = emptyString(),
        val passwordHelperText: String? = emptyString(),
        val isLoginSuccessful: Boolean = false
    )

    sealed class UiIntent {
        data class ValidateEmail(val email: String?) : UiIntent()
        data class ValidatePassword(val password: String?) : UiIntent()
        object DoLogin : UiIntent()
    }

    sealed class UiAction {
        data class Login(val email: String?, val password: String?) : UiAction()
    }
}