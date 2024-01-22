package com.pr.paymentreminder.presentation.viewModels.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiIntent
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiAction
import com.pr.paymentreminder.presentation.viewModels.register.RegisterViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    private val _isRegisterSuccessful = MutableSharedFlow<Boolean>()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
            is UiIntent.ValidatePasswordValidation -> validatePasswordValidation(intent.password, intent.passwordValidation)
            is UiIntent.Register -> register(intent.email, intent.password)
            UiIntent.GoLogin -> dispatchAction(UiAction.GoLogin)
        }
    }

    private fun validateEmail(email: String?) {
        val isEmailInvalid = !Patterns.EMAIL_ADDRESS.matcher(email.orEmpty()).matches() || email?.isEmpty() == true
        setState {
            copy(hasEmailHelperText = isEmailInvalid)
        }
    }

    private fun validatePassword(password: String?) {
        val isPasswordInvalid = password.isNullOrEmpty() || password.length < 8 || !password.matches(".*[0-9].*".toRegex())
        setState {
            copy(hasPasswordHelperText = isPasswordInvalid)
        }
    }

    private fun validatePasswordValidation(password: String?, passwordValidation: String?) {
        setState {
            copy(hasPasswordValidationHelperText = password != passwordValidation)
        }
    }

    private fun register(email: String?, password: String?) {
        viewModelScope.launch {
            registerUseCase.register(email.orEmpty(), password.orEmpty()).collect { result ->
                _isRegisterSuccessful.emit(result)
            }
        }
        dispatchAction(UiAction.Register)
    }
}