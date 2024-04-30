package com.pr.paymentreminder.data.use_case_implementations.services

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.service.GetServiceUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServiceUseCaseImpl@Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : GetServiceUseCase {
    override fun invoke(id: String): Flow<Service> =
        servicesDataSource.getService(id)
}