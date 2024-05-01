package com.pr.paymentreminder.data.use_case_implementations.notification_form

import com.pr.paymentreminder.data.model.Notification
import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.notification_form.SaveNotificationFormUseCase
import javax.inject.Inject

class SaveNotificationFormUseCaseImpl @Inject constructor(
    private val notificationDatabaseDataSource: NotificationDatabaseDataSource
) : SaveNotificationFormUseCase {
    override suspend fun invoke(form: Notification) =
        notificationDatabaseDataSource.saveNotificationForm(form)
}