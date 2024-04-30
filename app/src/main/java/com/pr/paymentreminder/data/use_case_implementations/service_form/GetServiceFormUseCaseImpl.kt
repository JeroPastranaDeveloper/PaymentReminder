package com.pr.paymentreminder.data.use_case_implementations.service_form

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.service_form.GetServiceFormUseCase
import javax.inject.Inject

class GetServiceFormUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : GetServiceFormUseCase {
    override suspend fun invoke(serviceId: String): Service? =
        serviceDatabaseDataSource.getServiceForm(serviceId)
}