package com.pr.servicesModule.data.source

import com.google.firebase.auth.FirebaseAuth
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SettingsDataSource @Inject constructor(
    private val preferencesHandler: PreferencesHandler
) {
    private val auth = FirebaseAuth.getInstance()
    private val _loginState = MutableStateFlow(false)

    fun signOut() {
        auth.signOut()
        _loginState.value = false
        preferencesHandler.hasToLogin = false
        preferencesHandler.email = null
        preferencesHandler.password = null
    }
}