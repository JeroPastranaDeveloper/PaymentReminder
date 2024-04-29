package com.pr.paymentreminder.di.service_form

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.source.AppDatabase
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.data.data_source_impls.ServiceDatabaseDataSourceImpl
import com.pr.paymentreminder.data.use_case_implementations.service_form.ServiceFormUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service_form.ServiceFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class ServiceFormModule {
    @Singleton
    @Provides
    fun providesServiceFormUseCase(serviceFormUseCaseImpl: ServiceFormUseCaseImpl): ServiceFormUseCase =
        serviceFormUseCaseImpl

    @Provides
    fun providesServiceDatabaseDataSource(
        appDatabase: AppDatabase,
        @CoroutineIO coroutineContext: CoroutineContext
    ) : ServiceDatabaseDataSource = ServiceDatabaseDataSourceImpl(
        appDatabase.serviceDao(),
        coroutineContext
    )
}