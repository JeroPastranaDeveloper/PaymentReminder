package com.pr.paymentreminder.data.source

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.consts.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState

    fun login(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val isLoggedIn = task.isSuccessful
                sharedPreferences.edit().putBoolean(Constants.HAS_TO_LOGIN, isLoggedIn).apply()
                _loginState.value = isLoggedIn
            }

    fun isUserAuthenticated(): Boolean = auth.currentUser != null || sharedPreferences.getBoolean(Constants.HAS_TO_LOGIN, false)

    fun signOut() {
        auth.signOut()
        sharedPreferences.edit().putBoolean(Constants.HAS_TO_LOGIN, false).apply()
        _loginState.value = false
    }

    fun hasToLogin(): Boolean = sharedPreferences.getBoolean(Constants.HAS_TO_LOGIN, false)
}