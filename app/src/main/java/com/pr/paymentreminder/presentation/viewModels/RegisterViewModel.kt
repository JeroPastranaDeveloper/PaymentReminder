package com.pr.paymentreminder.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _emailHelperText = MutableStateFlow<String?>(null)
    val emailHelperText: StateFlow<String?>
        get() = _emailHelperText

    private val _passHelperText = MutableStateFlow<String?>(null)
    val passHelperText: StateFlow<String?>
        get() = _passHelperText

    private val _repeatPassHelperText = MutableStateFlow<String?>(null)
    val repeatPassHelperText: StateFlow<String?>
        get() = _repeatPassHelperText

    private val _isRegisterSuccessful = MutableSharedFlow<Boolean>()
    val isRegisterSuccessful: SharedFlow<Boolean>
        get() = _isRegisterSuccessful

    var email: String = emptyString()
    var password: String = emptyString()
    var repeatPassword: String = emptyString()

    fun validateEmail(): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            _emailHelperText.value = R.string.invalid_email.toString()
            false
        } else {
            _emailHelperText.value = null
            true
        }
    }

    fun validatePasswordMatch() : Boolean {
        return if (password != repeatPassword) {
            _repeatPassHelperText.value = R.string.passwords_do_not_match.toString()
            false
        } else {
            _repeatPassHelperText.value = null
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

    fun register() {
        viewModelScope.launch {
            registerUseCase.register(email, password).collect { result ->
                _isRegisterSuccessful.emit(result)
            }
        }
    }
}