package com.pr.paymentreminder.di

import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.notifications.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesAlarmModule {
    @Singleton
    @Provides
    fun providesAlarmModule(repositoryImpl: AlarmSchedulerImpl): AlarmScheduler = repositoryImpl
}