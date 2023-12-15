package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterDataSource @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()

    private val _registerState = MutableStateFlow(false)
    val registerState: StateFlow<Boolean> = _registerState

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _registerState.value = task.isSuccessful
            }
    }
}