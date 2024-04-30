package com.pr.paymentreminder.domain.usecase.service_form

fun interface RemoveServiceFormUseCase {
    suspend operator fun invoke(serviceId: String)
}