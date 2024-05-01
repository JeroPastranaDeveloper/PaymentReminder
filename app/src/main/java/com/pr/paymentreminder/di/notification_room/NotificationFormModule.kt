package com.pr.paymentreminder.di.notification_room

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.data_source_impls.NotificationDatabaseDataSourceImpl
import com.pr.paymentreminder.data.source.AppDatabase
import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.data.use_case_implementations.notification_form.ClearAllNotificationFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.notification_form.GetAllNotificationFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.notification_form.GetNotificationFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.notification_form.RemoveNotificationFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.notification_form.SaveNotificationFormUseCaseImpl
import com.pr.paymentreminder.domain.usecase.notification_form.ClearAllNotificationFormsUseCase
import com.pr.paymentreminder.domain.usecase.notification_form.GetAllNotificationFormsUseCase
import com.pr.paymentreminder.domain.usecase.notification_form.GetNotificationFormUseCase
import com.pr.paymentreminder.domain.usecase.notification_form.RemoveNotificationFormUseCase
import com.pr.paymentreminder.domain.usecase.notification_form.SaveNotificationFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class NotificationFormModule {
    @Singleton
    @Provides
    fun providesClearAllNotificationFormsUseCase(clearAllNotificationFormsUseCaseImpl: ClearAllNotificationFormsUseCaseImpl): ClearAllNotificationFormsUseCase =
        clearAllNotificationFormsUseCaseImpl

    @Singleton
    @Provides
    fun providesRemoveNotificationFormUseCase(removeNotificationFormUseCaseImpl: RemoveNotificationFormUseCaseImpl): RemoveNotificationFormUseCase =
        removeNotificationFormUseCaseImpl

    @Singleton
    @Provides
    fun providesSaveNotificationFormUseCase(saveNotificationFormUseCaseImpl: SaveNotificationFormUseCaseImpl): SaveNotificationFormUseCase =
        saveNotificationFormUseCaseImpl

    @Singleton
    @Provides
    fun providesGetNotificationFormUseCase(getNotificationFormUseCaseImpl: GetNotificationFormUseCaseImpl): GetNotificationFormUseCase =
        getNotificationFormUseCaseImpl

    @Singleton
    @Provides
    fun providesGetAllNotificationFormsUseCase(getAllNotificationFormsUseCaseImpl: GetAllNotificationFormsUseCaseImpl): GetAllNotificationFormsUseCase =
        getAllNotificationFormsUseCaseImpl

    @Singleton
    @Provides
    fun providesNotificationDatabaseDataSource(
        appDatabase: AppDatabase,
        @CoroutineIO coroutineContext: CoroutineContext
    ) : NotificationDatabaseDataSource = NotificationDatabaseDataSourceImpl(
        appDatabase.notificationDao(),
        coroutineContext
    )
}