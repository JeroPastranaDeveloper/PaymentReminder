package com.pr.paymentreminder.data.source

import com.pr.paymentreminder.data.model.Service

interface ServiceDatabaseDataSource {
    suspend fun getServiceForm(serviceId: String) : Service?
    suspend fun getAllServicesForm() : List<Service>?
    suspend fun setServiceForm(form: Service)
    suspend fun clearServiceForm()
}