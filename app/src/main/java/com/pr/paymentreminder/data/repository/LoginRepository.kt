package com.pr.paymentreminder.data.repository

import kotlinx.coroutines.flow.StateFlow

interface LoginRepository {
    fun login(email: String, password: String): StateFlow<Boolean>
    fun isUserAuthenticated(): Boolean
    fun hasToLogin(): Boolean
    fun signOut()
}