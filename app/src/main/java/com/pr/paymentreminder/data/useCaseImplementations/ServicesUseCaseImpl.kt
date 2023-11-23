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

    override suspend fun createService(service: Service) {
        return repository.createService(service)
    }

    override suspend fun updateService(serviceName: String, newServiceData: Service) {
        return repository.updateService(serviceName, newServiceData)
    }

    override suspend fun deleteService(serviceName: String) {
        return repository.deleteService(serviceName)
    }
}