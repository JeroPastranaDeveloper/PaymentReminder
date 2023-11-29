package com.pr.paymentreminder.data.model

data class Service(
    var id: String,
    val category: String,
    val color: String,
    var date: String,
    var name: String,
    val price: String,
    val remember: String,
    val type: String,
    val image: String?
)