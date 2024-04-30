package com.pr.paymentreminder.domain.usecase.service_form

import com.pr.paymentreminder.data.model.Service

fun interface GetServiceFormUseCase {
    suspend operator fun invoke(serviceId: String): Service?
}