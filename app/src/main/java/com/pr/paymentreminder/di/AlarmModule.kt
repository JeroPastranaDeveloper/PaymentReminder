package com.pr.paymentreminder.di

import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.notifications.AlarmSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlarmModule {
    @Binds
    abstract fun bindsAlarmRepository(repositoryImpl: AlarmSchedulerImpl) : AlarmScheduler
}