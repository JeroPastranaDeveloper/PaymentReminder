package com.pr.paymentreminder.domain.usecase

import androidx.lifecycle.LiveData

interface LoginUseCase {
    suspend fun login(email: String, password: String): LiveData<Boolean>
    fun isUserAuthenticated(): Boolean
}