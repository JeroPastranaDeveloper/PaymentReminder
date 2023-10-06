package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.repository.ServiceRepository
import com.pr.paymentreminder.domain.usecase.ServiceUseCase

class ServiceUseCaseImpl(private val repository: ServiceRepository) : ServiceUseCase {
    override suspend fun getServices(): List<Service> {
        return repository.getServices()
    }
}