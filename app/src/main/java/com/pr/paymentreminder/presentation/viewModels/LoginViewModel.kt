package com.pr.paymentreminder.presentation.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun validateEmail(email: String, helperText: String) : Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            _emailHelperText.value = helperText
            false
        } else {
            _emailHelperText.value = null
            true
        }
    }

    fun validatePassword(password: String, helperText: String) : Boolean {
        return if (password.length < 8 || !password.matches(".*[0-9].*".toRegex()) || password.isEmpty()) {
            _passHelperText.value = helperText
            false
        } else {
            _passHelperText.value = null
            true
        }
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