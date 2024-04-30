package com.pr.paymentreminder.domain.usecase.service

import com.pr.paymentreminder.data.model.Service

fun interface UpdateServiceUseCase {
    suspend operator fun invoke(serviceId: String, newServiceData: Service)
}