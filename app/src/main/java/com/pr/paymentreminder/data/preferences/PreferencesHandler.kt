package com.pr.paymentreminder.data.preferences

interface PreferencesHandler {
    var email: String?
    var hasToLogin: Boolean
    var notificationsGranted: Boolean
    var password: String?
}