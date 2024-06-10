package com.pr.paymentreminder.mother

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
        isNotified: Boolean = false,
        image: String? = emptyString(),
        comments: String? = emptyString(),
        url: String? = emptyString()
    ) : Service = Service(
        id = id,
        category = category,
        color = color,
        date = date,
        name = name,
        price = price,
        remember = remember,
        type = type,
        isNotified = isNotified,
        image = image,
        comments = comments,
        url = url
    )
}