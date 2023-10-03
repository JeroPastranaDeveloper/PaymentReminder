package com.pr.paymentreminder.data.model

data class User(
    val name: String,
    val services: List<Service>
)

data class Service(
    val category: String,
    val color: String,
    val date: String,
    val name: String,
    val price: String,
    val remember: String,
    val type: String
)