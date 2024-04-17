package com.pr.paymentreminder.data.room

import com.pr.paymentreminder.base.CoroutineIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @CoroutineIO
    fun provideDefaultCoroutineIOContext(): CoroutineContext = Dispatchers.IO
}