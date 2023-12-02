package com.pr.paymentreminder.data.repository

import androidx.lifecycle.LiveData

interface RegisterRepository {
    suspend fun register(email: String, password: String): LiveData<Boolean>
}