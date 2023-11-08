package com.pr.paymentreminder.data.repository

import androidx.lifecycle.LiveData

interface LoginRepository {
    suspend fun login(email: String, password: String): LiveData<Boolean>
    fun isUserAuthenticated(): Boolean
}