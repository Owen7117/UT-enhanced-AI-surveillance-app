package com.owenoneil.aisecuritycamera

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AddDevice::class, AlertEntity::class, HistoryAlertEntity::class],
    version = 4 // bumped version to avoid schema mismatch
)
abstract class DevicesDatabase : RoomDatabase() {
    abstract fun deviceDao(): AddDeviceDao
    abstract fun alertDao(): AlertDao
    abstract fun historyAlertDao(): HistoryAlertDao

    companion object {
        @Volatile
        private var INSTANCE: DevicesDatabase? = null

        fun getInstance(context: Context): DevicesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DevicesDatabase::class.java,
                    "devices_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}