package com.pr.paymentreminder.data.use_case_implementations

import com.pr.paymentreminder.data.authentication.BiometricAuthenticator
import com.pr.paymentreminder.domain.usecase.BiometricAuthenticatorUseCase
import javax.inject.Inject

class BiometricAuthenticatorUseCaseImpl @Inject constructor(
    private val biometricAuthenticator: BiometricAuthenticator
) : BiometricAuthenticatorUseCase {
    override fun authenticate(onSuccess: () -> Unit) =
        biometricAuthenticator.authenticate(onSuccess)
}