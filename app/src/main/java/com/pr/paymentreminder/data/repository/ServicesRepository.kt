package com.pr.paymentreminder.data.repository

import com.pr.paymentreminder.data.model.Service

interface ServicesRepository {
    suspend fun getServices(): List<Service>
}