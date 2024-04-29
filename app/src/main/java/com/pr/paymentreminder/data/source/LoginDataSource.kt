package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginDataSource @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState

    fun login(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val isLoggedIn = task.isSuccessful
                _loginState.value = isLoggedIn
            }

    fun signOut() {
        auth.signOut()
        _loginState.value = false
    }
}