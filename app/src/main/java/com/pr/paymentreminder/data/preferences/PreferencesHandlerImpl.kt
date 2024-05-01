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
        private const val EMAIL = "email"
        private const val EXACT_ALARM_GRANTED = "exactAlarmGranted"
        private const val FIRST_TIME = "firstTime"
        private const val HAS_TO_LOGIN = "hasToLogin"
        private const val NOTIFICATIONS_GRANTED = "notificationsGranted"
        private const val PASSWORD = "password"
    }

    override var email: String?
        get() = sharedPreferences.getString(EMAIL, null)
        set(value) {
            this.edit.putString(EMAIL, value)?.apply()
        }

    override var exactAlarmGranted: Boolean
        get() = sharedPreferences.getBoolean(EXACT_ALARM_GRANTED, false)
        set(value) {
            this.edit.putBoolean(EXACT_ALARM_GRANTED, value)?.apply()
        }

    override var firstTime: Boolean
        get() = sharedPreferences.getBoolean(FIRST_TIME, true)
        set(value) {
            this.edit.putBoolean(FIRST_TIME, value)?.apply()
        }

    override var hasToLogin: Boolean
        get() = sharedPreferences.getBoolean(HAS_TO_LOGIN, false)
        set(value) {
            this.edit.putBoolean(HAS_TO_LOGIN, value)?.apply()
        }

    override var notificationsGranted: Boolean
        get() = sharedPreferences.getBoolean(NOTIFICATIONS_GRANTED, false)
        set(value) {
            this.edit.putBoolean(NOTIFICATIONS_GRANTED, value)?.apply()
        }

    override var password: String?
        get() = sharedPreferences.getString(PASSWORD, null)
        set(value) {
            this.edit.putString(PASSWORD, value)?.apply()
        }
}