package com.pr.paymentreminder.di.service_form

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.data_source_impls.CategoryDatabaseDataSourceImpl
import com.pr.paymentreminder.data.data_source_impls.ServiceDatabaseDataSourceImpl
import com.pr.paymentreminder.data.source.AppDatabase
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.data.use_case_implementations.service_form.ClearAllServiceFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.service_form.GetAllServiceFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.service_form.GetServiceFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.service_form.RemoveServiceFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.service_form.SaveServiceFormUseCaseImpl
import com.pr.paymentreminder.domain.usecase.service_form.ClearAllServiceFormsUseCase
import com.pr.paymentreminder.domain.usecase.service_form.GetAllServiceFormsUseCase
import com.pr.paymentreminder.domain.usecase.service_form.GetServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.service_form.RemoveServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
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
    fun providesClearAllServiceFormsUseCase(clearAllServiceFormsUseCaseImpl: ClearAllServiceFormsUseCaseImpl): ClearAllServiceFormsUseCase =
        clearAllServiceFormsUseCaseImpl

    @Singleton
    @Provides
    fun providesRemoveServiceFormUseCase(removeServiceFormUseCaseImpl: RemoveServiceFormUseCaseImpl): RemoveServiceFormUseCase =
        removeServiceFormUseCaseImpl

    @Singleton
    @Provides
    fun providesSaveServiceFormUseCase(saveServiceFormUseCaseImpl: SaveServiceFormUseCaseImpl): SaveServiceFormUseCase =
        saveServiceFormUseCaseImpl

    @Singleton
    @Provides
    fun providesGetServiceFormUseCase(getServiceFormUseCaseImpl: GetServiceFormUseCaseImpl): GetServiceFormUseCase =
        getServiceFormUseCaseImpl

    @Singleton
    @Provides
    fun providesGetAllServiceFormsUseCase(getAllServiceFormsUseCaseImpl: GetAllServiceFormsUseCaseImpl): GetAllServiceFormsUseCase =
        getAllServiceFormsUseCaseImpl

    @Provides
    fun providesServiceDatabaseDataSource(
        appDatabase: AppDatabase,
        @CoroutineIO coroutineContext: CoroutineContext
    ) : ServiceDatabaseDataSource = ServiceDatabaseDataSourceImpl(
        appDatabase.serviceDao(),
        coroutineContext
    )
}