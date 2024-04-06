package com.pr.paymentreminder.di

import com.pr.paymentreminder.providers.CurrentActivityProvider
import com.pr.paymentreminder.providers.PermissionsRequester
import com.pr.paymentreminder.providers.PermissionsRequesterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ProvidesPermissionRequester {
    @Singleton
    @Provides
    fun providesPermissionRequester(currentActivityProvider: CurrentActivityProvider): PermissionsRequester =
        PermissionsRequesterImpl(currentActivityProvider)
}