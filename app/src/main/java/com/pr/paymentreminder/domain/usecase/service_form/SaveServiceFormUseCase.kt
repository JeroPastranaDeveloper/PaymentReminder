package com.pr.paymentreminder.domain.usecase.service_form

import com.pr.paymentreminder.data.model.Service

fun interface SaveServiceFormUseCase {
    suspend operator fun invoke(form: Service)
}