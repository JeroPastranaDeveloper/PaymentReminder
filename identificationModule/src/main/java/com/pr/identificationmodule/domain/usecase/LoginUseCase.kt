package com.pr.identificationmodule.domain.usecase

import kotlinx.coroutines.flow.StateFlow

interface LoginUseCase {
    fun login(email: String, password: String): StateFlow<Boolean>
    fun isUserAuthenticated(): Boolean
    fun signOut()
}