package com.pr.paymentreminder.data.repositoryImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServiceRepository
import com.pr.paymentreminder.data.source.ServiceDataSource
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor (
    private val datasource: ServiceDataSource
) : ServiceRepository {
    override suspend fun getServices(): List<Service> {
        return datasource.getServices()
    }
}