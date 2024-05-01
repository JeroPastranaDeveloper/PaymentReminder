package com.pr.paymentreminder.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE service_id = :serviceId")
    fun getNotificationForm(serviceId: String): NotificationRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNotificationRoom(form: NotificationRoom)

    @Query("SELECT * FROM notifications")
    fun getAllForms(): List<NotificationRoom>?

    @Delete
    fun deleteForm(form: NotificationRoom)
}