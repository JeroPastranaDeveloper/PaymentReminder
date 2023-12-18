package com.pr.paymentreminder.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _emailHelperText = MutableStateFlow<String?>(null)
    val emailHelperText: StateFlow<String?>
        get() = _emailHelperText

    private val _passHelperText = MutableStateFlow<String?>(null)
    val passHelperText: StateFlow<String?>
        get() = _passHelperText

    private val _isLoginSuccessful = MutableSharedFlow<Boolean>()
    val isLoginSuccessful: SharedFlow<Boolean>
        get() = _isLoginSuccessful

    var email: String = emptyString()
    var password: String = emptyString()

    init {
        autoLogin()
    }

    fun validateEmail(): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            _emailHelperText.value = R.string.invalid_email.toString()
            false
        } else {
            _emailHelperText.value = null
            true
        }
    }

    fun validatePassword(): Boolean {
        return if (password.length < 8 || !password.matches(".*[0-9].*".toRegex()) || password.isEmpty()) {
            _passHelperText.value = R.string.invalid_pass.toString()
            false
        } else {
            _passHelperText.value = null
            true
        }
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase.login(email, password).collect { result ->
                _isLoginSuccessful.emit(result)
            }
        }
    }

    private fun autoLogin() {
        viewModelScope.launch {
            val hasToLogin = loginUseCase.hasToLogin()
            _isLoginSuccessful.emit(hasToLogin)
        }
    }
}