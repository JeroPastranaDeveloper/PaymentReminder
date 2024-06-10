package com.pr.paymentreminder.data.room.service

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "services",
    primaryKeys = ["service_id"]
)

data class ServiceRoom(
    @ColumnInfo("service_id") var serviceId: String,
    @ColumnInfo("category") var category: String,
    @ColumnInfo("date") var date: String,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("price") var price: String,
    @ColumnInfo("type") var type: String,
    @ColumnInfo("comments") var comments: String
)