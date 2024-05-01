package com.pr.paymentreminder.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pr.paymentreminder.data.room.NotificationDao
import com.pr.paymentreminder.data.room.NotificationRoom
import com.pr.paymentreminder.data.room.ServiceDao
import com.pr.paymentreminder.data.room.ServiceRoom

@Database(
    entities = [
        ServiceRoom::class,
        NotificationRoom::class
    ],
    version = 4,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "app-room-database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun serviceDao(): ServiceDao
    abstract fun notificationDao(): NotificationDao
}