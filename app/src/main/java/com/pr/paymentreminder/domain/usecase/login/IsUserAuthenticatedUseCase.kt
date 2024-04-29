package com.pr.paymentreminder.domain.usecase.login

fun interface IsUserAuthenticatedUseCase {
    operator fun invoke(): Boolean
}