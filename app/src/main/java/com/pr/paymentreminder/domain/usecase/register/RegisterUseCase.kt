package com.pr.paymentreminder.domain.usecase.register

import kotlinx.coroutines.flow.StateFlow

fun interface RegisterUseCase {
    suspend fun register(email: String, password: String): StateFlow<Boolean>
}