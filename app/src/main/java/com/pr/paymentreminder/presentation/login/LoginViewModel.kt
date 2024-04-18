package com.pr.paymentreminder.presentation.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesHandler: PreferencesHandler
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
            is UiIntent.DoLogin -> login(intent.email, intent.password)
            UiIntent.GoRegister -> dispatchAction(UiAction.GoRegister)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.login(email, password)
        }
        preferencesHandler.hasToLogin = true
        preferencesHandler.email = email
        preferencesHandler.password = password
        dispatchAction(UiAction.Login)
    }

    init {
        if (preferencesHandler.hasToLogin) {
            login(preferencesHandler.email.orEmpty(), preferencesHandler.password.orEmpty())
        }
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
}