package com.pr.paymentreminder.data.repositoryImplementations

import androidx.lifecycle.LiveData
import com.pr.paymentreminder.data.repository.RegisterRepository
import com.pr.paymentreminder.data.source.RegisterDataSource
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterRepository {
    override suspend fun register(email: String, password: String): LiveData<Boolean> {
        return registerDataSource.register(email, password)
    }
}