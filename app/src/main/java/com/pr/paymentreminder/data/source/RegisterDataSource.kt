package com.pr.paymentreminder.data.source

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.consts.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)

    private val _registerState = MutableStateFlow(false)
    val registerState: StateFlow<Boolean> = _registerState

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val registered = task.isSuccessful
                sharedPreferences.edit().putBoolean(Constants.HAS_TO_LOGIN, registered).apply()
                _registerState.value = registered
            }
    }
}