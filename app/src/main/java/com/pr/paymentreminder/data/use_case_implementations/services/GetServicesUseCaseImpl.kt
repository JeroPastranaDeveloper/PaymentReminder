package com.pr.paymentreminder.data.use_case_implementations.services

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.source.ServicesDataSource
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetServicesUseCaseImpl @Inject constructor(
    private val servicesDataSource: ServicesDataSource
) : GetServicesUseCase {
    private var services: Flow<List<Service>> = emptyFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<List<Service>> {
        services = servicesDataSource.getServices()
        return services
    }
}