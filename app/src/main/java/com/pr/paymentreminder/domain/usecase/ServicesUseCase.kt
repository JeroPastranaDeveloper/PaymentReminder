package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

interface ServicesUseCase {
    suspend fun getServices(): List<Service>
    suspend fun createService(id: String, service: Service)
    suspend fun updateService(serviceId: String, newServiceData: Service)
    suspend fun deleteService(serviceId: String)
}