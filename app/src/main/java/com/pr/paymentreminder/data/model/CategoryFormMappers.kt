package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.data.room.category.CategoryRoom

fun CategoryRoom.toDomain() = Category(
    id = categoryId,
    name = categoryName
)

fun Category.toEntity() = CategoryRoom(
    categoryId = id,
    categoryName = name
)