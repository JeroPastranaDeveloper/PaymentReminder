package com.pr.mylibrary

import android.content.Context
import android.content.Intent
import com.pr.identificationmodule.presentation.login.LoginActivity

fun interface LoginNavigator {
    fun navigateToLogin()
}

class LoginActivityNavigator(private val context: Context) : LoginNavigator {
    override fun navigateToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }
}
