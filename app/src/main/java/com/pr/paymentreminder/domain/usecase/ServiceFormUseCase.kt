package com.pr.paymentreminder.domain.usecase

import com.pr.paymentreminder.data.model.Service

interface ServiceFormUseCase {
    suspend fun getServiceForm(serviceId: String) : Service?
    suspend fun setServiceForm(form: Service)
    suspend fun clearServiceForm()
}