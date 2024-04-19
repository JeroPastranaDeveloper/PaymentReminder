package com.pr.paymentreminder.mothers

import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyString

object ServiceMother {
    fun buildService(
        id: String = emptyString(),
        category: String = emptyString(),
        color: String = emptyString(),
        date: String = emptyString(),
        name: String = emptyString(),
        price: String = emptyString(),
        remember: String = emptyString(),
        type: String = emptyString(),
        image: String = emptyString(),
        comments: String = emptyString(),
        url: String = emptyString()
    ) : Service = Service(
        id, category, color, date, name, price, remember, type, image, comments, url
    )
}