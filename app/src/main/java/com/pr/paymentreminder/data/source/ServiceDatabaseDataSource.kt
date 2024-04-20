package com.pr.paymentreminder.data.source

import com.pr.paymentreminder.data.model.Service

interface ServiceDatabaseDataSource {
    suspend fun clearServiceForm()
    suspend fun getAllServicesForm() : List<Service>?
    suspend fun getServiceForm(serviceId: String) : Service?
    suspend fun removeService(serviceId: String)
    suspend fun setServiceForm(form: Service)
}