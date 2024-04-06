package com.pr.paymentreminder.data.source

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterDataSource @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) {
    private val auth = FirebaseAuth.getInstance()

    private val _registerState = MutableStateFlow(false)
    val registerState: StateFlow<Boolean> = _registerState

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val isRegistered = task.isSuccessful
                preferencesHandler.hasToLogin = isRegistered
                _registerState.value = isRegistered
            }
    }
}