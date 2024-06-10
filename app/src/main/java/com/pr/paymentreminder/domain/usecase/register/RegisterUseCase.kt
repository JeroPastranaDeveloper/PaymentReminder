package com.pr.paymentreminder.domain.usecase.register

fun interface RegisterUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean
}