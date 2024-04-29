package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.CreateServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CreateServiceModule {
    @Singleton
    @Provides
    fun providesCreateServiceUseCase(createServiceUseCaseImpl: CreateServiceUseCaseImpl):
            CreateServiceUseCase = createServiceUseCaseImpl
}