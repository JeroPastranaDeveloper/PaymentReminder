package com.pr.paymentreminder.data.use_case_implementations.service_form

import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.service_form.RemoveServiceFormUseCase
import javax.inject.Inject

class RemoveServiceFormUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : RemoveServiceFormUseCase {
    override suspend fun invoke(serviceId: String) =
        serviceDatabaseDataSource.removeService(serviceId)
}