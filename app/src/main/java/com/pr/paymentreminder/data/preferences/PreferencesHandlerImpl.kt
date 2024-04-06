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
    }

    override var hasToLogin: Boolean
        get() = sharedPreferences.getBoolean(HAS_TO_LOGIN, false)
        set(value) {
            this.edit.putBoolean(HAS_TO_LOGIN, value)?.apply()
        }
}