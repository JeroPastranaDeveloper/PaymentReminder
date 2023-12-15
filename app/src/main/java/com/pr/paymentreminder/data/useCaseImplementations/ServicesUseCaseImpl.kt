package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ServicesUseCaseImpl @Inject constructor(
    private val repository: ServicesRepository
) : ServicesUseCase {
    private var services: Flow<List<Service>> = emptyFlow()

    override fun getServices(): Flow<List<Service>> {
        services = repository.getServices()
        return services
    }

    override fun getFilteredServices(filter: String): Flow<List<Service>> {
        return services.map { serviceList ->
            when (filter) {
                Constants.ALL_SERVICES -> serviceList
                PaymentType.WEEKLY.type -> serviceList.filter { it.type == PaymentType.WEEKLY.type }
                PaymentType.MONTHLY.type -> serviceList.filter { it.type == PaymentType.MONTHLY.type }
                PaymentType.YEARLY.type -> serviceList.filter { it.type == PaymentType.YEARLY.type }
                else -> serviceList
            }
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