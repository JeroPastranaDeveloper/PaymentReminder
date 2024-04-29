package com.pr.paymentreminder.domain.usecase.register

import kotlinx.coroutines.flow.StateFlow

fun interface RegisterUseCase {
    suspend operator fun invoke(email: String, password: String): StateFlow<Boolean>
}