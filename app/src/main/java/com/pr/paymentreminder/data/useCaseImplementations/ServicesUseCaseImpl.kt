package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import javax.inject.Inject

class ServicesUseCaseImpl @Inject constructor(
    private val repository: ServicesRepository
) : ServicesUseCase {
    private var services: List<Service> = emptyList()

    override suspend fun getServices(): List<Service> {
        services = repository.getServices()
        return services
    }

    override suspend fun getFilteredServices(filter: String): List<Service> {
        return when (filter) {
            Constants.ALL_SERVICES -> services
            PaymentType.WEEKLY.type -> services.filter { it.type == PaymentType.WEEKLY.type }
            PaymentType.MONTHLY.type -> services.filter { it.type == PaymentType.MONTHLY.type }
            PaymentType.YEARLY.type -> services.filter { it.type == PaymentType.YEARLY.type }
            else -> services
        }
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
