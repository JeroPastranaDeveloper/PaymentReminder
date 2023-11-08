package com.pr.paymentreminder.data.repository

import com.pr.paymentreminder.data.model.Service

fun interface ServicesRepository {
    suspend fun getServices(): List<Service>
}