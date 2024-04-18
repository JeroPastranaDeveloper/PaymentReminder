package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.useCaseImplementations.ServiceFormUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.ServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
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

    @Singleton
    @Provides
    fun providesServiceFormUseCase(serviceFormUseCaseImpl: ServiceFormUseCaseImpl): ServiceFormUseCase =
        serviceFormUseCaseImpl
}