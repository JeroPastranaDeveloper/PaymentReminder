package com.pr.paymentreminder.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _emailHelperText = MutableLiveData<String?>()
    val emailHelperText: LiveData<String?>
        get() = _emailHelperText

    private val _passHelperText = MutableLiveData<String?>()
    val passHelperText: LiveData<String?>
        get() = _passHelperText

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    fun validateEmail(email: String) : Boolean {
        val isValid : Boolean
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            _emailHelperText.value = R.string.invalid_email.toString()
            isValid = false
        } else {
            _emailHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun validatePassword(password: String) : Boolean {
        val isValid : Boolean
        if (password.length < 8 || !password.matches(".*[0-9].*".toRegex()) || password.isEmpty()) {
            _passHelperText.value = R.string.invalid_pass.toString()
            isValid = false
        } else {
            _passHelperText.value = null
            isValid = true
        }
        return isValid
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.login(email, password).observeForever { result ->
                _isLoginSuccessful.value = result
            }
        }
    }

    fun checkIfUserIsAuthenticated() {
        val isUserAuthenticated = loginUseCase.isUserAuthenticated()
        _isLoginSuccessful.value = isUserAuthenticated
    }
}