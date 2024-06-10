package com.pr.paymentreminder.di.login

import com.pr.paymentreminder.data.use_case_implementations.login.LoginUseCaseImpl
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import com.pr.paymentreminder.presentation.login.EmailValidator
import com.pr.paymentreminder.presentation.login.EmailValidatorImpl
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

    @Singleton
    @Provides
    fun providesEmailValidator(emailValidatorImpl: EmailValidatorImpl): EmailValidator = emailValidatorImpl
}