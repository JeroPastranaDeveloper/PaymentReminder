package com.pr.identificationmodule.domain.usecase

import kotlinx.coroutines.flow.StateFlow

fun interface RegisterUseCase {
    suspend fun register(email: String, password: String): StateFlow<Boolean>
}