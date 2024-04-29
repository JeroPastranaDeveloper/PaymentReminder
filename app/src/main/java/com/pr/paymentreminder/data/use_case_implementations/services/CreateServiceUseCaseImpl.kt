package com.pr.paymentreminder.data.use_case_implementations.services

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import javax.inject.Inject

class CreateServiceUseCaseImpl @Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : CreateServiceUseCase {
    override suspend fun invoke(id: String, service: Service) =
        servicesDataSource.createService(id, service)
}