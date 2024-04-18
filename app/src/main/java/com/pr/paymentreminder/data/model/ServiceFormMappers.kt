package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.data.room.ServiceRoom

fun ServiceRoom.toDomain() = Service(
    id = serviceId,
    category = category,
    date = date,
    name = name,
    price = price,
    type = type,
    comments = comments
)

fun Service.toEntity() = ServiceRoom(
    serviceId = id,
    category = category,
    date = date,
    name = name,
    price = price,
    type = type,
    comments = comments.orEmpty()
)