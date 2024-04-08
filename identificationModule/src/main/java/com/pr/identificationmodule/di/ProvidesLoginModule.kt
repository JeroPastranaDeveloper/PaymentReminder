package com.pr.identificationmodule.di

import com.pr.identificationmodule.data.usecase_implementations.LoginUseCaseImpl
import com.pr.identificationmodule.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesLoginModule {
    @Singleton
    @Provides
    fun providesLoginUseCase(useCaseImpl: LoginUseCaseImpl) : LoginUseCase = useCaseImpl
}