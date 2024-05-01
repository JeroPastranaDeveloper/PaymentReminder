package com.pr.paymentreminder.domain.usecase.service_form

fun interface ClearAllServiceFormsUseCase {
    suspend operator fun invoke()
}