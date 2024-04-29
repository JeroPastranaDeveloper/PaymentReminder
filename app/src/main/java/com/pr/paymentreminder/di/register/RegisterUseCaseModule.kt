package com.pr.paymentreminder.di.register

import com.pr.paymentreminder.data.use_case_implementations.login.LoginUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.register.RegisterUseCaseImpl
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import com.pr.paymentreminder.domain.usecase.register.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegisterUseCaseModule {
    @Singleton
    @Provides
    fun providesRegisterUseCase(useCaseImpl: RegisterUseCaseImpl): RegisterUseCase = useCaseImpl
}