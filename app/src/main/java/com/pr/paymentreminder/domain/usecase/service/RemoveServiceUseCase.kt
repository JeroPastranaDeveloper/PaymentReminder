package com.pr.paymentreminder.domain.usecase.service

fun interface RemoveServiceUseCase {
    suspend operator fun invoke(serviceId: String)
}