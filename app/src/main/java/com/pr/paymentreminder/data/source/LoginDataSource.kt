package com.pr.paymentreminder.data.source

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val auth = FirebaseAuth.getInstance()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPreferences.edit().putBoolean("hasToLogin", true).apply()
                }
                _loginState.value = task.isSuccessful
            }
    }

    fun isUserAuthenticated(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null || sharedPreferences.getBoolean("hasToLogin", false)
    }

    fun signOut() {
        auth.signOut()
        sharedPreferences.edit().putBoolean("hasToLogin", false).apply()
        _loginState.value = false
    }

    fun hasToLogin(): Boolean = sharedPreferences.getBoolean("hasToLogin", false)
}