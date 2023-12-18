package com.pr.paymentreminder.domain.usecase

import kotlinx.coroutines.flow.StateFlow

interface LoginUseCase {
    fun login(email: String, password: String): StateFlow<Boolean>
    fun isUserAuthenticated(): Boolean
    fun hasToLogin(): Boolean
    fun signOut()
}