package com.pr.paymentreminder.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "notifications",
    primaryKeys = ["service_id"]
)

data class NotificationRoom(
    @ColumnInfo("service_id") var serviceId: String,
    @ColumnInfo("payment_date") var paymentDate: String,
    @ColumnInfo("notification_date") var notificationDate: String,
    @ColumnInfo("is_notified") var isNotified: Boolean
)
