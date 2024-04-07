package com.pr.paymentreminder.providers

import android.app.Application

fun interface Bootstrap {
    fun init(app: Application)
}