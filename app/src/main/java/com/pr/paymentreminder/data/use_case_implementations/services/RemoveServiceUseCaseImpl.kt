package com.pr.paymentreminder.data.use_case_implementations.services

import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import javax.inject.Inject

class RemoveServiceUseCaseImpl @Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : RemoveServiceUseCase {
    override suspend fun invoke(serviceId: String) =
        servicesDataSource.removeService(serviceId)
}