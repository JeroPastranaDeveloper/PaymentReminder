package com.pr.paymentreminder.data.use_case_implementations.service_form

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.service_form.GetAllServiceFormsUseCase
import javax.inject.Inject

class GetAllServiceFormsUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : GetAllServiceFormsUseCase {
    override suspend fun invoke(): List<Service>? =
        serviceDatabaseDataSource.getAllServicesForm()
}