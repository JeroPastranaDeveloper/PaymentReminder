package com.pr.paymentreminder.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _emailHelperText = MutableLiveData<String?>()
    val emailHelperText: LiveData<String?>
        get() = _emailHelperText

    private val _passHelperText = MutableLiveData<String?>()
    val passHelperText: LiveData<String?>
        get() = _passHelperText
    private val _repeatPassHelperText = MutableLiveData<String?>()
    val repeatPassHelperText: LiveData<String?>
        get() = _repeatPassHelperText

    private val _isRegisterSuccessful = MutableLiveData<Boolean>()
    val isRegisterSuccessful: LiveData<Boolean>
        get() = _isRegisterSuccessful

    fun validateEmail(email: String, emailHelper: String) : Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            _emailHelperText.value = emailHelper
            false
        } else {
            _emailHelperText.value = null
            true
        }
    }

    fun validatePasswordMatch(password: String, repeatPassword: String) : Boolean {
        return if (password != repeatPassword) {
            _repeatPassHelperText.value = R.string.passwords_do_not_match.toString()
            false
        } else {
            _repeatPassHelperText.value = null
            true
        }
    }

    fun validatePassword(password: String) : Boolean {
        return if (password.length < 8 || !password.matches(".*[0-9].*".toRegex()) || password.isEmpty()) {
            _passHelperText.value = R.string.invalid_pass.toString()
            false
        } else {
            _passHelperText.value = null
            true
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase.register(email, password).observeForever { result ->
                _isRegisterSuccessful.value = result
            }
        }
    }
}