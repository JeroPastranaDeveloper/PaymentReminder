package com.pr.paymentreminder.presentation.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.register.RegisterUseCase
import com.pr.paymentreminder.presentation.register.RegisterViewContract.UiIntent
import com.pr.paymentreminder.presentation.register.RegisterViewContract.UiAction
import com.pr.paymentreminder.presentation.register.RegisterViewContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val preferencesHandler: PreferencesHandler
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ValidateEmail -> validateEmail(intent.email)
            is UiIntent.ValidatePassword -> validatePassword(intent.password)
            is UiIntent.ValidatePasswordValidation -> validatePasswordValidation(
                intent.password,
                intent.passwordValidation
            )
            UiIntent.GoLogin -> dispatchAction(UiAction.GoLogin)
            is UiIntent.CheckData -> checkData(
                intent.email,
                intent.password,
                intent.passwordValidation
            )
        }
    }

    private fun checkData(email: String?, password: String?, repeatPassword: String?) {
        viewModelScope.launch {
            val jobsGroup = listOf(
                viewModelScope.async {
                    validateEmail(email)
                },
                viewModelScope.async {
                    validatePassword(password)
                },
                viewModelScope.async {
                    validatePasswordValidation(password, repeatPassword)
                }
            )

            jobsGroup.awaitAll()

            if (state.value.email.isNotEmpty() && state.value.password.isNotEmpty() && state.value.passwordValidation.isNotEmpty()
                && !state.value.hasEmailHelperText && !state.value.hasPasswordHelperText && !state.value.hasPasswordValidationHelperText
            ) {
                register(email, password)
            }
        }
    }


    private fun validateEmail(email: String?) {
        val isEmailInvalid =
            !Patterns.EMAIL_ADDRESS.matcher(email.orEmpty()).matches() || email?.isEmpty() == true
        setState {
            copy(
                hasEmailHelperText = isEmailInvalid,
                email = email.orEmpty()
            )
        }
    }

    private fun validatePassword(password: String?) {
        val isPasswordInvalid =
            password.isNullOrEmpty() || password.length < 8 || !password.matches(".*[0-9].*".toRegex())
        setState {
            copy(
                hasPasswordHelperText = isPasswordInvalid,
                password = password.orEmpty()
            )
        }
    }

    private fun validatePasswordValidation(password: String?, passwordValidation: String?) {
        setState {
            copy(
                hasPasswordValidationHelperText = password != passwordValidation,
                passwordValidation = passwordValidation.orEmpty()
            )
        }
    }

    private fun register(email: String?, password: String?) {
        viewModelScope.launch {
            registerUseCase.register(email.orEmpty(), password.orEmpty())
        }
        preferencesHandler.email = email
        preferencesHandler.password = password
        preferencesHandler.hasToLogin = true
        dispatchAction(UiAction.Register)
    }
}