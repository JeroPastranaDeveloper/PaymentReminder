package com.pr.paymentreminder.domain.usecase

import androidx.lifecycle.LiveData

interface RegisterUseCase {
    suspend fun register(email: String, password: String): LiveData<Boolean>
}