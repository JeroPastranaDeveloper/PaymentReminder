package com.pr.paymentreminder.data.room.category

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "categories",
    primaryKeys = ["category_id"]
)

data class CategoryRoom (
    @ColumnInfo("category_id") var categoryId: Int,
    @ColumnInfo("category_name") var categoryName: String
)