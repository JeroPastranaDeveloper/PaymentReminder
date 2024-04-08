package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.usecase_implementation.ServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesServicesModule {
    @Singleton
    @Provides
    fun providesServicesUseCase(useCaseImpl: ServicesUseCaseImpl) : ServicesUseCase = useCaseImpl
}