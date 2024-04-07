package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ServicesUseCaseImpl @Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : ServicesUseCase {
    private var services: Flow<List<Service>> = emptyFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getServices(): Flow<List<Service>> {
        services = servicesDataSource.getServices()
        return services
    }

    override fun getService(id: String): Flow<Service> =
        servicesDataSource.getService(id)

    override suspend fun createService(id: String, service: Service) =
        servicesDataSource.createService(id, service)

    override suspend fun updateService(serviceId: String, newServiceData: Service) =
        servicesDataSource.updateService(serviceId, newServiceData)

    override suspend fun deleteService(serviceId: String) =
        servicesDataSource.deleteService(serviceId)
}