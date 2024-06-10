package com.pr.paymentreminder.domain.usecase.login

fun interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean
}