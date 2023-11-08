package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

fun interface ServicesUseCase {
    suspend fun getServices(): List<Service>
}