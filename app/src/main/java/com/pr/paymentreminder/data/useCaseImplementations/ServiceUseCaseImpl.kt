package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServiceRepository
import com.pr.paymentreminder.domain.usecase.ServiceUseCase
import javax.inject.Inject

class ServiceUseCaseImpl @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase {
    override suspend fun getServices(): List<Service> {
        return repository.getServices()
    }
}