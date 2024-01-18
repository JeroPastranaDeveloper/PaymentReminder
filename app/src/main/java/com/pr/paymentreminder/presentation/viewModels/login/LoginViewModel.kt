package com.pr.paymentreminder.presentation.viewModels.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.login.LoginViewContract.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
            is UiIntent.DoLogin -> login(intent.email, intent.password)
            UiIntent.AutoLogin -> autoLogin()
            UiIntent.GoRegister -> dispatchAction(UiAction.GoRegister)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.login(email, password)
        }
        dispatchAction(UiAction.Login)
    }

    init {
        autoLogin()
    }

    private fun validateEmail(email: String) {
        val isEmailInvalid = !Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
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

    private fun autoLogin() {
        viewModelScope.launch {
            val hasToLogin = loginUseCase.hasToLogin()
            setState {
                copy(
                    isLoginSuccessful = hasToLogin
                )
            }
        }

        if (state.value.isLoginSuccessful) {
            dispatchAction(UiAction.Login)
        }
    }
}