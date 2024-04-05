package com.pr.paymentreminder.di

import com.pr.paymentreminder.providers.CurrentActivityProvider
import com.pr.paymentreminder.providers.CurrentActivityProviderImpl
import com.pr.paymentreminder.providers.PermissionsRequester
import com.pr.paymentreminder.providers.PermissionsRequesterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class CurrentActivityModules {
    /*@Binds
    abstract fun bindsCurrentActivityProvider(currentActivityProvider: CurrentActivityProviderImpl) : CurrentActivityProvider

*/
    @ViewModelScoped
    @Provides
    fun providesCurrentActivity(
        currentActivityProvider: CurrentActivityProviderImpl
    ) : CurrentActivityProvider {
        return currentActivityProvider
    }
}