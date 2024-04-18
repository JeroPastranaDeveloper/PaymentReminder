package com.pr.paymentreminder.di

import android.content.Context
import com.pr.paymentreminder.data.source.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideRoomAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.build(context)
}