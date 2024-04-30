package com.pr.paymentreminder.domain.usecase.service_form

import com.pr.paymentreminder.data.model.Service

fun interface GetAllServiceFormsUseCase {
    suspend operator fun invoke(): List<Service>?
}