package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.data.consts.Constants

enum class Categories (val category: String) {
    AMAZON(Constants.AMAZON),
    HOBBY(Constants.HOBBY),
    PLATFORMS(Constants.PLATFORMS)
}