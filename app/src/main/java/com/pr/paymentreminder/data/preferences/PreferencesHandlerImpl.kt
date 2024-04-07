package com.pr.paymentreminder.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesHandlerImpl(context: Context) : PreferencesHandler {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    private val edit = sharedPreferences.edit()

    fun clear() {
        edit.clear().apply()
    }

    companion object {
        private const val HAS_TO_LOGIN = "hasToLogin"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }

    override var hasToLogin: Boolean
        get() = sharedPreferences.getBoolean(HAS_TO_LOGIN, false)
        set(value) {
            this.edit.putBoolean(HAS_TO_LOGIN, value)?.apply()
        }

    override var email: String?
        get() = sharedPreferences.getString(EMAIL, null)
        set(value) {
            this.edit.putString(EMAIL, value)?.apply()
        }

    override var password: String?
        get() = sharedPreferences.getString(PASSWORD, null)
        set(value) {
            this.edit.putString(PASSWORD, value)?.apply()
        }
}