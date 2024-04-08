package com.pr.servicesModule.di

import android.content.Context
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.data.preferences.PreferencesHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferencesHandler(@ApplicationContext app: Context): PreferencesHandler =
        PreferencesHandlerImpl(app)
}
