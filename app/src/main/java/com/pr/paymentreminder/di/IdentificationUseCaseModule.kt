package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.useCaseImplementations.LoginUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.RegisterUseCaseImpl
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class IdentificationUseCaseModule {
    @Singleton
    @Provides
    fun providesLoginUseCase(useCaseImpl: LoginUseCaseImpl): LoginUseCase = useCaseImpl

    @Singleton
    @Provides
    fun providesRegisterUseCase(useCaseImpl: RegisterUseCaseImpl): RegisterUseCase = useCaseImpl
}