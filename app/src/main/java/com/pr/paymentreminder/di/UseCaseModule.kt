package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.useCaseImplementations.LoginUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.RegisterUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.ServicesUseCaseImpl
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindsLoginUseCase(useCaseImpl: LoginUseCaseImpl) : LoginUseCase

    @Binds
    abstract fun bindsRegisterUseCase(useCaseImpl: RegisterUseCaseImpl) : RegisterUseCase

    @Binds
    abstract fun bindsServicesUseCase(useCaseImpl: ServicesUseCaseImpl) : ServicesUseCase
}