package com.pr.paymentreminder.di.service

import com.pr.paymentreminder.data.use_case_implementations.services.RemoveServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoveServiceModule {
    @Singleton
    @Provides
    fun providesRemoveServiceUseCase(removeServiceUseCaseImpl: RemoveServiceUseCaseImpl):
            RemoveServiceUseCase = removeServiceUseCaseImpl
}