package com.pr.paymentreminder.domain.usecase.service

import com.pr.paymentreminder.data.model.Service

fun interface CreateServiceUseCase {
    suspend operator fun invoke(id: String, service: Service)
}