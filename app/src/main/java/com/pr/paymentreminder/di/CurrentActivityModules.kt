package com.pr.paymentreminder.di

import com.pr.paymentreminder.providers.CurrentActivityProvider
import com.pr.paymentreminder.providers.CurrentActivityProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CurrentActivityModules {
    @Singleton
    @Provides
    fun providesCurrentActivity(currentActivityProvider: CurrentActivityProviderImpl): CurrentActivityProvider =
        currentActivityProvider
}