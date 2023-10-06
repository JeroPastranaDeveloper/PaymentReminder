package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.data.repository.ServiceRepository
import com.pr.paymentreminder.data.repositoryImplementations.LoginRepositoryImpl
import com.pr.paymentreminder.data.repositoryImplementations.ServiceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsLoginRepository(repositoryImpl: LoginRepositoryImpl) : LoginRepository

    @Binds
    abstract fun bindsServiceRepository(repositoryImpl: ServiceRepositoryImpl) : ServiceRepository
}