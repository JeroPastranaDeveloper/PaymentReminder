package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.UpdateServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UpdateServiceModule {
    @Singleton
    @Provides
    fun providesUpdateServiceUseCase(updateServiceUseCaseImpl: UpdateServiceUseCaseImpl):
            UpdateServiceUseCase = updateServiceUseCaseImpl
}