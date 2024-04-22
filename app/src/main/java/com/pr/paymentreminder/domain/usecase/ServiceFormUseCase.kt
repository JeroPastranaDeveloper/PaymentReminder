package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

interface ServiceFormUseCase {
    suspend fun clearAllServicesForm()
    suspend fun getAllServicesForm() : List<Service>?
    suspend fun getServiceForm(serviceId: String) : Service?
    suspend fun removeService(serviceId: String)
    suspend fun setServiceForm(form: Service)
}