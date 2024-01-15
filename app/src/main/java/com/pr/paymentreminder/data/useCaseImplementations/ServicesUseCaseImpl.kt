package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ServicesUseCaseImpl @Inject constructor(
    private val repository: ServicesRepository
) : ServicesUseCase {
    private var services: Flow<List<Service>> = emptyFlow()

    override fun getServices(): Flow<List<Service>> {
        services = repository.getServices()
        return services
    }

    override suspend fun createService(id: String, service: Service) {
        repository.createService(id, service)
    }

    override suspend fun updateService(serviceId: String, newServiceData: Service) {
        repository.updateService(serviceId, newServiceData)
    }

    override suspend fun deleteService(serviceId: String) {
        repository.deleteService(serviceId)
    }
}