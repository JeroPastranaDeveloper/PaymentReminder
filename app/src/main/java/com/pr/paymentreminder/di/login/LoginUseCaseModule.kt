package com.pr.paymentreminder.di.login

import com.pr.paymentreminder.data.use_case_implementations.login.LoginUseCaseImpl
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginUseCaseModule {
    @Singleton
    @Provides
    fun providesLoginUseCase(useCaseImpl: LoginUseCaseImpl): LoginUseCase = useCaseImpl
}