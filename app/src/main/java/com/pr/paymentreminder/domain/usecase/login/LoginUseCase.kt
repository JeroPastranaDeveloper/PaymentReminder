package com.pr.paymentreminder.domain.usecase.login

import kotlinx.coroutines.flow.StateFlow

fun interface LoginUseCase {
    operator fun invoke(email: String, password: String): StateFlow<Boolean>
}