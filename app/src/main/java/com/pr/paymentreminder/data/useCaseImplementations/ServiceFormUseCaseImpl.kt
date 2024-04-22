package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import javax.inject.Inject

class ServiceFormUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : ServiceFormUseCase {
    override suspend fun clearAllServicesForm() =
        serviceDatabaseDataSource.clearAllServicesForm()

    override suspend fun getAllServicesForm(): List<Service>? =
        serviceDatabaseDataSource.getAllServicesForm()

    override suspend fun getServiceForm(serviceId: String): Service? =
        serviceDatabaseDataSource.getServiceForm(serviceId)

    override suspend fun removeService(serviceId: String) =
        serviceDatabaseDataSource.removeService(serviceId)

    override suspend fun setServiceForm(form: Service) =
        serviceDatabaseDataSource.setServiceForm(form)
}