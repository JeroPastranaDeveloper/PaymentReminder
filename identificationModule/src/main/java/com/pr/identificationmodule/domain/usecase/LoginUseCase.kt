package com.pr.identificationmodule.domain.usecase

import kotlinx.coroutines.flow.StateFlow

fun interface LoginUseCase {
    fun login(email: String, password: String): StateFlow<Boolean>
}