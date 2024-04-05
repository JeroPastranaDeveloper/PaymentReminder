package com.pr.paymentreminder.di

import com.pr.paymentreminder.providers.CurrentActivityProvider
import com.pr.paymentreminder.providers.PermissionsRequester
import com.pr.paymentreminder.providers.PermissionsRequesterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
class ProvidesPermissionRequester {

    @ViewModelScoped
    @Provides
    fun providesPermissionRequester(
        currentActivityProvider: CurrentActivityProvider
    ): PermissionsRequester {
        return PermissionsRequesterImpl(currentActivityProvider)
    }
}