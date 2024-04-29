package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.GetServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetServicesModule {
    @Singleton
    @Provides
    fun providesGetServicesUseCase(useCaseImpl: GetServicesUseCaseImpl): GetServicesUseCase = useCaseImpl
}