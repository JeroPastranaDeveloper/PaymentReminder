package com.pr.paymentreminder.data.preferences

interface PreferencesHandler {
    var email: String?
    var exactAlarmGranted: Boolean
    var firstTime: Boolean
    var hasToLogin: Boolean
    var notificationsGranted: Boolean
    var password: String?
}