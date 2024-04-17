package com.pr.paymentreminder.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services WHERE service_id = :serviceId")
    fun getServiceForm(serviceId: String): ServiceRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setServiceRoom(form: ServiceRoom)

    @Query("SELECT * from services")
    fun getAllForms(): List<ServiceRoom>?

    @Delete
    fun deleteForm(form: ServiceRoom)
}