package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.ServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.ServicesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Singleton
    @Provides
    fun providesServicesUseCase(useCaseImpl: ServicesUseCaseImpl): ServicesUseCase = useCaseImpl
}