package com.pr.paymentreminder.data.repositoryImplementations

import com.pr.paymentreminder.data.repository.RegisterRepository
import com.pr.paymentreminder.data.source.RegisterDataSource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterRepository {
    override suspend fun register(email: String, password: String): StateFlow<Boolean> {
        registerDataSource.register(email, password)
        return registerDataSource.registerState
    }
}