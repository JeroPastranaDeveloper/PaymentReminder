package com.pr.paymentreminder.domain.usecase.notification_form

import com.pr.paymentreminder.data.model.Notification

fun interface GetAllNotificationFormsUseCase {
    suspend operator fun invoke(): List<Notification>?
}