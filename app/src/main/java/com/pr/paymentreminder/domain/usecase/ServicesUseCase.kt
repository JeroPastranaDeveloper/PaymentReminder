package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

interface ServicesUseCase {
    suspend fun getServices(): List<Service>
    suspend fun createService(service: Service)
    suspend fun updateService(serviceName: String, newServiceData: Service)
    suspend fun deleteService(serviceName: String)
}