package com.pr.paymentreminder.data.preferences

interface PreferencesHandler {
    var hasToLogin: Boolean
    var email: String?
    var password: String?
}