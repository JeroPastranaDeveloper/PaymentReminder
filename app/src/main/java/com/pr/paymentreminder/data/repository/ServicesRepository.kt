package com.pr.paymentreminder.data.repository

import com.pr.paymentreminder.data.model.Service
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {
    fun getServices(): Flow<List<Service>>
    fun getService(id: String): Flow<Service>
    suspend fun createService(id: String, service: Service)
    suspend fun updateService(serviceId: String, newServiceData: Service)
    suspend fun deleteService(serviceId: String)
}