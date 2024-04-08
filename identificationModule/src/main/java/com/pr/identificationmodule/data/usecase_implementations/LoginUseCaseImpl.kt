package com.pr.identificationmodule.data.usecase_implementations

import com.pr.identificationmodule.data.source.LoginDataSource
import com.pr.identificationmodule.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginUseCase {
    override fun login(email: String, password: String): StateFlow<Boolean> {
        loginDataSource.login(email, password)
        return loginDataSource.loginState
    }
}