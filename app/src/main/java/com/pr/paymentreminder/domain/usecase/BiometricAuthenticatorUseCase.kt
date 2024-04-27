package com.pr.paymentreminder.domain.usecase

fun interface BiometricAuthenticatorUseCase {
    fun authenticate(onSuccess: () -> Unit)
}