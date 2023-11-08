package com.pr.paymentreminder.data.repositoryImplementations

import androidx.lifecycle.LiveData
import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.data.source.LoginDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) : LoginRepository {
    override suspend fun login(email: String, password: String): LiveData<Boolean> {
        return dataSource.login(email, password)
    }

    override fun isUserAuthenticated(): Boolean {
        return dataSource.isUserAuthenticated()
    }

    override fun signOut() = dataSource.signOut()
}