package com.pr.paymentreminder.data.room

import android.content.Context
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