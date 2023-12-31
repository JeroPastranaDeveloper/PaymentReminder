package com.pr.paymentreminder.data.repositoryImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.data.source.ServicesDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor (
    private val dataSource: ServicesDataSource
) : ServicesRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getServices(): Flow<List<Service>> {
        return dataSource.getServices()
    }

    override suspend fun createService(id: String, service: Service) {
        return dataSource.createService(id, service)
    }

    override suspend fun updateService(serviceId: String, newServiceData: Service) {
        return dataSource.updateService(serviceId, newServiceData)
    }

    override suspend fun deleteService(serviceId: String) {
        return dataSource.deleteService(serviceId)
    }
}