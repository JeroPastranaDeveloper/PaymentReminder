package com.pr.paymentreminder.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseHeaderComposeViewModelWithActions
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.LoginViewContract.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginNewViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseHeaderComposeViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    private val _isLoginSuccessful = MutableSharedFlow<Boolean>()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
            UiIntent.DoLogin -> dispatchAction(UiAction.Login(state.value.email, state.value.password))
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
        }
    }
    init {
        autoLogin()
    }

    private fun validateEmail(email: String?) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.orEmpty()).matches() || email?.isEmpty() == true) {
            setState {
                copy(
                    hasEmailHelperText = true
                )
            }
        }
    }

    private fun validatePassword(password: String?) {
        if (password.isNullOrEmpty() || password.length < 8 || !password.matches(".*[0-9].*".toRegex())) {
            setState {
                copy(
                    hasPasswordHelperText = true
                )
            }
        } else {
            setState {
                copy(
                    hasPasswordHelperText = false
                )
            }
        }
    }

    private fun autoLogin() {
        viewModelScope.launch {
            val hasToLogin = loginUseCase.hasToLogin()
            _isLoginSuccessful.emit(hasToLogin)
            setState {
                copy(
                    isLoginSuccessful = hasToLogin
                )
            }
        }
    }
}