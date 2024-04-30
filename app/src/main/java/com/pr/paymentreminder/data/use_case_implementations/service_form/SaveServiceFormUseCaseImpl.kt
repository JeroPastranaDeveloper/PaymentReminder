package com.pr.paymentreminder.data.use_case_implementations.service_form

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
import javax.inject.Inject

class SaveServiceFormUseCaseImpl @Inject constructor(
    private val serviceDatabaseDataSource: ServiceDatabaseDataSource
) : SaveServiceFormUseCase {
    override suspend fun invoke(form: Service) =
        serviceDatabaseDataSource.saveServiceForm(form)
}