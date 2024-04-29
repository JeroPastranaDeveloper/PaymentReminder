package com.pr.paymentreminder.domain.usecase.service

import com.pr.paymentreminder.data.model.Service
import kotlinx.coroutines.flow.Flow

interface ServicesUseCase {
    fun getServices(): Flow<List<Service>>
    fun invoke(id: String) : Flow<Service>
    suspend fun createService(id: String, service: Service)
    suspend fun updateService(serviceId: String, newServiceData: Service)
    suspend fun removeService(serviceId: String)
}