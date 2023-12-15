package com.pr.paymentreminder.data.repository

import kotlinx.coroutines.flow.StateFlow

fun interface RegisterRepository {
    suspend fun register(email: String, password: String): StateFlow<Boolean>
}