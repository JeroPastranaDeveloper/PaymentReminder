package com.pr.servicesModule.di

import com.pr.paymentreminder.data.usecase_implementation.SettingsUseCaseImpl
import com.pr.paymentreminder.domain.usecase.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesSettingsModule {
    @Singleton
    @Provides
    fun providesSettingsUseCase(useCaseImpl: SettingsUseCaseImpl) : SettingsUseCase = useCaseImpl
}