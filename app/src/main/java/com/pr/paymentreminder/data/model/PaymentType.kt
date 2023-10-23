package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.data.consts.Constants

enum class PaymentType(val type: String) {
    WEEKLY(Constants.WEEKLY),
    MONTHLY(Constants.MONTHLY),
    YEARLY(Constants.YEARLY)
}