package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.GetServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.GetServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetServiceModule {
    @Singleton
    @Provides
    fun providesGetServiceUseCase(getServiceUseCaseImpl: GetServiceUseCaseImpl): GetServiceUseCase = getServiceUseCaseImpl
}