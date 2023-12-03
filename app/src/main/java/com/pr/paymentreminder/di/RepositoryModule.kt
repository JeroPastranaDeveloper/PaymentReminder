package com.pr.paymentreminder.di

import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.data.repository.RegisterRepository
import com.pr.paymentreminder.data.repository.ServicesRepository
import com.pr.paymentreminder.data.repositoryImplementations.LoginRepositoryImpl
import com.pr.paymentreminder.data.repositoryImplementations.RegisterRepositoryImpl
import com.pr.paymentreminder.data.repositoryImplementations.ServicesRepositoryImpl
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
    abstract fun bindsRegisterRepository(repositoryImpl: RegisterRepositoryImpl) : RegisterRepository

    @Binds
    abstract fun bindsServicesRepository(repositoryImpl: ServicesRepositoryImpl) : ServicesRepository
}