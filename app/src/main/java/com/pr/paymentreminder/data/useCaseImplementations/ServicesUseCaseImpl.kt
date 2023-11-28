package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import javax.inject.Inject

class ServicesUseCaseImpl @Inject constructor(
    private val repository: ServicesRepository
) : ServicesUseCase {
    override suspend fun getServices(): List<Service> {
        return repository.getServices()
    }

    override suspend fun createService(id: String, service: Service) {
        return repository.createService(id, service)
    }

    override suspend fun updateService(serviceId: String, newServiceData: Service) {
        return repository.updateService(serviceId, newServiceData)
    }

    override suspend fun deleteService(serviceId: String) {
        return repository.deleteService(serviceId)
    }
}