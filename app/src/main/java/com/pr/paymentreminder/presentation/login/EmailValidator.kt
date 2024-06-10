package com.pr.paymentreminder.presentation.login

import android.util.Patterns
import javax.inject.Inject

fun interface EmailValidator {
    fun validate(email: String): Boolean
}

class EmailValidatorImpl @Inject constructor() : EmailValidator {
    override fun validate(email: String): Boolean = !Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()
}