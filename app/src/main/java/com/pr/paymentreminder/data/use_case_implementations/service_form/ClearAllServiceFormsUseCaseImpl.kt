package com.pr.paymentreminder.data.use_case_implementations.service_form

import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.service_form.ClearAllServiceFormsUseCase
import javax.inject.Inject

class ClearAllServiceFormsUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : ClearAllServiceFormsUseCase {
    override suspend fun invoke() =
        serviceDatabaseDataSource.clearAllServicesForm()
}