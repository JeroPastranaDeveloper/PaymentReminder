package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.useCaseImplementations.LoginUseCaseImpl
import com.pr.paymentreminder.data.useCaseImplementations.ServiceUseCaseImpl
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.domain.usecase.ServiceUseCase
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
    abstract fun bindsServiceUseCase(useCaseImpl: ServiceUseCaseImpl) : ServiceUseCase
}