package com.pr.paymentreminder.di.login

import com.pr.paymentreminder.data.use_case_implementations.login.SignOutUseCaseImpl
import com.pr.paymentreminder.domain.usecase.login.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SignOutModule {
    @Singleton
    @Provides
    fun providesSignOutUseCase(signOutUseCaseImpl: SignOutUseCaseImpl): SignOutUseCase = signOutUseCaseImpl
}