package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

interface ServiceUseCase {
    suspend fun getServices(): List<Service>
}