package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.useCaseImplementations.LoginUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.RegisterUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.ServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun providesLoginUseCase(useCaseImpl: LoginUseCaseImpl) : LoginUseCase = useCaseImpl

    @Singleton
    @Provides
    fun providesRegisterUseCase(useCaseImpl: RegisterUseCaseImpl) : RegisterUseCase = useCaseImpl

    @Singleton
    @Provides
    fun providesServicesUseCase(useCaseImpl: ServicesUseCaseImpl) : ServicesUseCase = useCaseImpl
}