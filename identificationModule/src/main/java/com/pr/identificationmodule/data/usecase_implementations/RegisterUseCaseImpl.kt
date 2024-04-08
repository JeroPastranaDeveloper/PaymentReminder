package com.pr.identificationmodule.data.usecase_implementations

import com.pr.identificationmodule.data.source.RegisterDataSource
import com.pr.identificationmodule.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterUseCase {
    override suspend fun register(email: String, password: String): StateFlow<Boolean> {
        registerDataSource.register(email, password)
        return registerDataSource.registerState
    }
}