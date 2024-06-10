package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class RegisterDataSource @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) {
    private val auth = FirebaseAuth.getInstance()

    suspend fun register(email: String, password: String)  = suspendCancellableCoroutine { c ->
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val isSuccessful = task.isSuccessful
                c.resume(isSuccessful)
                preferencesHandler.hasToLogin = isSuccessful
            }
    }
}