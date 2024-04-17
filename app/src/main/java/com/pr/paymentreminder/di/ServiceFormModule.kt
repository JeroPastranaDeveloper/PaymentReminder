package com.pr.paymentreminder.di

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.room.AppDatabase
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class ServiceFormModule {
    @Provides
    fun providesServiceDatabaseDataSource(
        appDatabase: AppDatabase,
        @CoroutineIO coroutineContext: CoroutineContext
    ) : ServiceDatabaseDataSource = ServiceDatabaseDataSourceImpl(
        appDatabase.serviceDao(),
        coroutineContext
    )
}