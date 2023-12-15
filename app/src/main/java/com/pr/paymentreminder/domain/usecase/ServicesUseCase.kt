package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service
import kotlinx.coroutines.flow.Flow

interface ServicesUseCase {
    fun getServices(): Flow<List<Service>>
    fun getFilteredServices(filter: String): Flow<List<Service>>
    suspend fun createService(id: String, service: Service)
    suspend fun updateService(serviceId: String, newServiceData: Service)
    suspend fun deleteService(serviceId: String)
}