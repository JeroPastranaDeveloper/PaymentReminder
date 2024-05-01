package com.pr.paymentreminder.domain.usecase.notification_form

fun interface ClearAllNotificationFormsUseCase {
    suspend operator fun invoke()
}