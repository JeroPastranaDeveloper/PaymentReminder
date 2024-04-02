package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.ui.theme.emptyString

data class Service(
    var id: String = emptyString(),
    val category: String = emptyString(),
    val color: String = emptyString(),
    var date: String = emptyString(),
    var name: String = emptyString(),
    val price: String = emptyString(),
    val remember: String = emptyString(),
    val type: String = emptyString(),
    val image: String? = emptyString(),
    val comments: String? = emptyString(),
    val url: String? = emptyString()
)