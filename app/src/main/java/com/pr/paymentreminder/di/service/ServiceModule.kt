package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.CreateServiceUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.services.GetServiceUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.services.GetServicesUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.services.RemoveServiceUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.services.UpdateServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Singleton
    @Provides
    fun providesCreateServiceUseCase(createServiceUseCaseImpl: CreateServiceUseCaseImpl):
            CreateServiceUseCase = createServiceUseCaseImpl

    @Singleton
    @Provides
    fun providesRemoveServiceUseCase(removeServiceUseCaseImpl: RemoveServiceUseCaseImpl):
            RemoveServiceUseCase = removeServiceUseCaseImpl

    @Singleton
    @Provides
    fun providesGetServiceUseCase(getServiceUseCaseImpl: GetServiceUseCaseImpl): GetServiceUseCase = getServiceUseCaseImpl

    @Singleton
    @Provides
    fun providesGetServicesUseCase(useCaseImpl: GetServicesUseCaseImpl): GetServicesUseCase = useCaseImpl

    @Singleton
    @Provides
    fun providesUpdateServiceUseCase(updateServiceUseCaseImpl: UpdateServiceUseCaseImpl):
            UpdateServiceUseCase = updateServiceUseCaseImpl
}