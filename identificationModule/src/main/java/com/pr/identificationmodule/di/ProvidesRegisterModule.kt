package com.pr.identificationmodule.di

import com.pr.identificationmodule.data.usecase_implementations.RegisterUseCaseImpl
import com.pr.identificationmodule.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesRegisterModule {
    @Singleton
    @Provides
    fun providesRegisterUseCase(useCaseImpl: RegisterUseCaseImpl) : RegisterUseCase = useCaseImpl
}