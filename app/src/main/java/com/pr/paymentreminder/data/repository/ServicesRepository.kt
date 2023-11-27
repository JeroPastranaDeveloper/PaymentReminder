package com.pr.paymentreminder.data.repository

import com.pr.paymentreminder.data.model.Service

interface ServicesRepository {
    suspend fun getServices(): List<Service>
    suspend fun createService(service: Service)
    suspend fun updateService(serviceId: String, newServiceData: Service)
    suspend fun deleteService(serviceName: String)
}