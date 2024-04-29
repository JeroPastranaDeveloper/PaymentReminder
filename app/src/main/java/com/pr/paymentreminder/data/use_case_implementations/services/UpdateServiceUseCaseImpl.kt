package com.pr.paymentreminder.data.use_case_implementations.services

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import javax.inject.Inject

class UpdateServiceUseCaseImpl @Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : UpdateServiceUseCase {
    override suspend fun invoke(serviceId: String, newServiceData: Service) =
        servicesDataSource.updateService(serviceId, newServiceData)
}