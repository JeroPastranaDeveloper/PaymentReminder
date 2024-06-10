package com.pr.paymentreminder.data.source

import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LoginDataSource @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String) = suspendCancellableCoroutine { c ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                c.resume(task.isSuccessful)
            }
    }


    fun signOut() {
        auth.signOut()
        preferencesHandler.hasToLogin = false
    }
}