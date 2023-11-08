package com.pr.paymentreminder.domain.usecase

import androidx.lifecycle.LiveData

fun interface LoginUseCase {
    suspend fun login(email: String, password: String): LiveData<Boolean>
}